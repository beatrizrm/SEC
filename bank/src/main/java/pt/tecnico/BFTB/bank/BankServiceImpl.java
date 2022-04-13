package pt.tecnico.BFTB.bank;

import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import pt.tecnico.BFTB.bank.exceptions.AccountAlreadyExistsException;
import pt.tecnico.BFTB.bank.exceptions.AccountDoesntExistException;
import pt.tecnico.BFTB.bank.exceptions.AccountPermissionException;
import pt.tecnico.BFTB.bank.exceptions.InsufficientBalanceException;
import pt.tecnico.BFTB.bank.exceptions.NonceAlreadyExistsException;
import pt.tecnico.BFTB.bank.exceptions.TransactionAlreadyCompletedException;
import pt.tecnico.BFTB.bank.exceptions.TransactionDoesntExistException;
import pt.tecnico.BFTB.bank.crypto.CryptoHelper;
import pt.tecnico.BFTB.bank.grpc.*;
import pt.tecnico.BFTB.bank.pojos.Transaction;

import java.sql.SQLException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.grpc.Status.INVALID_ARGUMENT;
import static io.grpc.Status.FAILED_PRECONDITION;
import static io.grpc.Status.ABORTED;
import static io.grpc.Status.ALREADY_EXISTS;
import static io.grpc.Status.NOT_FOUND;
import static io.grpc.Status.UNKNOWN;
import static io.grpc.Status.PERMISSION_DENIED;

public class BankServiceImpl extends BankServiceGrpc.BankServiceImplBase{
    private BankManager bankAccounts;
    private String dbUrl, dbUser, dbPw;
    private HashMap<PublicKey, String> pendingRequests;

    public BankServiceImpl(String dbName, String dbUser, String dbPw) throws IOException {
        this.dbUrl = "jdbc:postgresql:" + dbName;
        this.dbUser = dbUser;
        this.dbPw = dbPw;
        this.bankAccounts = new BankManager();
        this.pendingRequests = new HashMap<PublicKey, String>();
    }

    @Override
    public void openAccount(openAccountRequest request, StreamObserver<openAccountResponse> responseObserver) {
        String key = request.getKey();
        String user = request.getUser();
        String requestId = request.getRequestId();

        if (key == null || key.isBlank()) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("openAccount: Client Key cannot be empty!").asRuntimeException());
            return;
        }

        BankData db = new BankData();
        int status = 0;

        try {
            db.connect(dbUrl, dbUser, dbPw);
            bankAccounts.openAccount(db, user, key);
            bankAccounts.setOperationStatus(db, key, requestId, 1);
            status = 1;
        } catch (AccountAlreadyExistsException e) {
            responseObserver.onError(ALREADY_EXISTS.withDescription("openAccount: " + e.getMessage()).asRuntimeException());
            bankAccounts.setOperationStatus(db, key, requestId, 0);
            return;
        } catch (SQLException e) {
            responseObserver.onError(UNKNOWN.withDescription("openAccount: Error connecting to database.").asRuntimeException());
            printError("openAccount", "DB error - " + e.getMessage());
            bankAccounts.setOperationStatus(db, key, requestId, 0);
            return;
        } finally {
            db.closeConnection();
        }

        Status status1 = Status.newBuilder().setStatus(status).build();
        PrivateKey serverKey = CryptoHelper.readRSAPrivateKey(CryptoHelper.private_path + "/server.priv");

        // sign message so client knows that was server who send it
        String signature = CryptoHelper.encodeToBase64(CryptoHelper.signMessage(serverKey,status1.toByteArray()));

        openAccountResponse response = openAccountResponse.newBuilder().setStatus(status1).setSignature(signature).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void sendAmount(sendAmountRequest request, StreamObserver<sendAmountResponse> responseObserver) {

        //get message and verify signature
        sendAmountContent msg = request.getMessage();
        PublicKey key_source = CryptoHelper.publicKeyFromBase64(msg.getSource());
        PublicKey key_destiny = CryptoHelper.publicKeyFromBase64(msg.getDestination());
        String requestId = msg.getRequestId();

        //verify signature
        if (!CryptoHelper.verifySignature(msg.toByteArray(),request.getSignature(),key_source)) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("sendAmount: Signature not verified").asRuntimeException());
            return;
        }

        // verify nonce
        String nonce = pendingRequests.get(key_source);
        if (nonce == null || !nonce.equals(msg.getNonce())) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("sendAmount: Incorrect nonce").asRuntimeException());
            return;
        }

        String key_destination = msg.getDestination();
        String amount = msg.getAmount();
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

        if (key_source == null) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("sendAmount: Source Key cannot be empty!").asRuntimeException());
            return;
        }

        if (key_destiny == null) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("sendAmount: Destination Key cannot be empty!").asRuntimeException());
            return;
        }

        if (msg.getSource().equals(msg.getDestination())) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("sendAmount: Source and destination cannot be the same!").asRuntimeException());
            return;
        }

        if (amount == null || amount.isBlank() || Integer.parseInt(amount) <= 0) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("sendAmount: Amount cannot be empty and must be greater than 0!").asRuntimeException());
            return;
        }

        BankData db = new BankData();
        int status = 0;
        try {
            db.connect(dbUrl, dbUser, dbPw);
            bankAccounts.addNonce(db, nonce);
            db.beginTransaction();
            bankAccounts.checkIfTransactionPossible(db, key_source, key_destiny, Integer.parseInt(amount));
            Transaction transaction = new Transaction(0, msg.getSource(), msg.getDestination(), 0, Integer.parseInt(amount), 0, timeStamp);
            bankAccounts.addTransactionHistory(db, transaction, request.getSignature());
            bankAccounts.setOperationStatus(db, msg.getSource(), requestId, 1);
            db.commit();
            status = 1;
        } catch (AccountDoesntExistException e) {
            responseObserver.onError(NOT_FOUND.withDescription("sendAmount: " + e.getMessage()).asRuntimeException());
            db.rollback();
            bankAccounts.setOperationStatus(db, msg.getSource(), requestId, 0);
            return;
        } catch (InsufficientBalanceException e) {
            responseObserver.onError(FAILED_PRECONDITION.withDescription("sendAmount: " + e.getMessage()).asRuntimeException());
            db.rollback();
            bankAccounts.setOperationStatus(db, msg.getSource(), requestId, 0);
            return;
        } catch (NonceAlreadyExistsException e) {
            responseObserver.onError(PERMISSION_DENIED.withDescription("sendAmount: " + e.getMessage()).asRuntimeException());
            return;
        } catch (SQLException e) {
            responseObserver.onError(ABORTED.withDescription("sendAmount: Error connecting to database.").asRuntimeException());
            printError("sendAmount", "DB error - " + e.getMessage());
            bankAccounts.setOperationStatus(db, msg.getSource(), requestId, 0);
            db.rollback();
            return;
        } finally {
            db.closeConnection();
        }

        Status status1 = Status.newBuilder().setStatus(status).build();

        // sign message so client knows that was server who send it
        PrivateKey serverKey = CryptoHelper.readRSAPrivateKey(CryptoHelper.private_path + "/server.priv");
        String signature = CryptoHelper.encodeToBase64(CryptoHelper.signMessage(serverKey,status1.toByteArray()));

        sendAmountResponse response = sendAmountResponse.newBuilder().setStatus(status1).setSignature(signature).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void checkAccount(checkAccountRequest request, StreamObserver<checkAccountResponse> responseObserver) {

        String key_string = request.getKey();
        PublicKey key = CryptoHelper.publicKeyFromBase64(key_string);

        if (key == null) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("checkAccount: Client Key cannot be empty!").asRuntimeException());
            return;
        }

        BankData db = new BankData();
        String balance = "";
        List<Transaction> transactionHistory = null;
        try {
            db.connect(dbUrl, dbUser, dbPw);
            balance = String.valueOf(bankAccounts.checkBalance(db, key_string));
            transactionHistory = bankAccounts.checkTransactions(db, key_string);
        } catch (AccountDoesntExistException e) {
            responseObserver.onError(NOT_FOUND.withDescription("checkAccount: " + e.getMessage()).asRuntimeException());
            return;
        } catch (SQLException e) {
            responseObserver.onError(UNKNOWN.withDescription("checkAccount: Error connecting to database.").asRuntimeException());
            printError("checkAccount", "DB error - " + e.getMessage());
            return;
        } finally {
            db.closeConnection();
        }

        String pendingTransactions = "";
        String newline = ""; 
        for(Transaction temp: transactionHistory){
            if(temp.getStatus() == 0){
                pendingTransactions += newline + temp.toString();
                newline = "\n";
            }
        }

        //create the response message
        checkAccountResMsg msg = checkAccountResMsg.newBuilder().setBalance(balance).setPendingTransactions(pendingTransactions).build();

        // sign message so client knows that was server who send it
        PrivateKey serverKey = CryptoHelper.readRSAPrivateKey(CryptoHelper.private_path + "/server.priv");
        String signature = CryptoHelper.encodeToBase64(CryptoHelper.signMessage(serverKey,msg.toByteArray()));


        checkAccountResponse response = checkAccountResponse.newBuilder().setMsgResponse(msg).setSignature(signature).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void receiveAmount(receiveAmountRequest request, StreamObserver<receiveAmountResponse> responseObserver) {

        receiveAmountContent msg = request.getMsg();
        String key = msg.getKey();
        String signature = request.getSignature();
        String transactionID = msg.getTransactionId();
        String requestId = msg.getRequestId();

        //verify signature
        if (!CryptoHelper.verifySignature(msg.toByteArray(),signature,CryptoHelper.publicKeyFromBase64(key))) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("receiveAmount: Signature not verified").asRuntimeException());
            return;
        }

        // verify nonce
        String nonce = pendingRequests.get(CryptoHelper.publicKeyFromBase64(key));
        if (nonce == null || !nonce.equals(msg.getNonce())) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("receiveAmount: Incorrect nonce").asRuntimeException());
            return;
        }

        if (key == null || key.isBlank()) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("receiveAmount: Client Key cannot be empty!").asRuntimeException());
            return;
        }

        if (transactionID == null || transactionID.isBlank()) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("receiveAmount: TransactionId cannot be empty!").asRuntimeException());
            return;
        }

        BankData db = new BankData();
        int status = 0;
        try {
            db.connect(dbUrl, dbUser, dbPw);
            bankAccounts.addNonce(db, nonce);
            db.beginTransaction();
            bankAccounts.receiveAmount(db, CryptoHelper.publicKeyFromBase64(key), transactionID, signature);
            bankAccounts.setOperationStatus(db, key, requestId, 1);
            db.commit();
            status = 1;
        } catch (AccountDoesntExistException | TransactionDoesntExistException e) {
            responseObserver.onError(NOT_FOUND.withDescription("receiveAmount: " + e.getMessage()).asRuntimeException());
            db.rollback();
            bankAccounts.setOperationStatus(db, key, requestId, 0);
            return;
        } catch (TransactionAlreadyCompletedException | InsufficientBalanceException e) {
            responseObserver.onError(FAILED_PRECONDITION.withDescription("receiveAmount: " + e.getMessage()).asRuntimeException());
            db.rollback();
            bankAccounts.setOperationStatus(db, key, requestId, 0);
            return;
        } catch (AccountPermissionException e) {
            responseObserver.onError(PERMISSION_DENIED.withDescription("receiveAmount: " + "User isn't the destinatary of the transaction").asRuntimeException());
            db.rollback();
            bankAccounts.setOperationStatus(db, key, requestId, 0);
            return;
        } catch (NonceAlreadyExistsException e) {
            responseObserver.onError(PERMISSION_DENIED.withDescription("receiveAmount: " + e.getMessage()).asRuntimeException());
            return;
        } catch (SQLException e) {
            responseObserver.onError(ABORTED.withDescription("receiveAmount: Error connecting to database.").asRuntimeException());
            printError("receiveAmount", "DB error - " + e.getMessage());
            db.rollback();
            bankAccounts.setOperationStatus(db, key, requestId, 0);
            return;
        } finally {
            db.closeConnection();
        }

        Status status1 = Status.newBuilder().setStatus(status).build();
        PrivateKey serverKey = CryptoHelper.readRSAPrivateKey(CryptoHelper.private_path + "/server.priv");

        // sign message so client knows that was server who send it
        signature = CryptoHelper.encodeToBase64(CryptoHelper.signMessage(serverKey,status1.toByteArray()));

        receiveAmountResponse response = receiveAmountResponse.newBuilder().setStatus(status1).setSignature(signature).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void audit(auditRequest request, StreamObserver<auditResponse> responseObserver) {
        String key_string = request.getKey();
        PublicKey key = CryptoHelper.publicKeyFromBase64(key_string);

        if (key == null) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("audit: Client Key cannot be empty!").asRuntimeException());
            return;
        }

        BankData db = new BankData();
        List<Transaction> transactions = null;
        try {
            db.connect(dbUrl, dbUser, dbPw);
            transactions = bankAccounts.checkTransactions(db, key_string);
        } catch (AccountDoesntExistException e) {
            responseObserver.onError(NOT_FOUND.withDescription("audit: " + e.getMessage()).asRuntimeException());
            return;
        } catch (SQLException e) {
            responseObserver.onError(UNKNOWN.withDescription("audit: Error connecting to database.").asRuntimeException());
            printError("audit", "DB error - " + e.getMessage());
            return;
        } finally {
            db.closeConnection();
        }

        String transactionHistory = "";
        String newline = ""; 
        for(Transaction temp: transactions){
            transactionHistory += newline + temp.toString();
            newline = "\n";
        }

        //sign the message with server private key
        PrivateKey serverKey = CryptoHelper.readRSAPrivateKey(CryptoHelper.private_path + "/server.priv");
        String signature = CryptoHelper.encodeToBase64(CryptoHelper.signMessage(serverKey,transactionHistory.getBytes(StandardCharsets.UTF_8)));

        auditResponse response = auditResponse.newBuilder().setTransactionHistory(transactionHistory).setSignature(signature).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void checkStatus(checkStatusRequest request, StreamObserver<checkStatusResponse> responseObserver) {
        String key = request.getKey();
        String requestId = request.getRequestId();

        if (key == null) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("checkStatus: Client Key cannot be empty!").asRuntimeException());
            return;
        }
        if (requestId == null) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("checkStatus: Request id cannot be empty!").asRuntimeException());
            return;
        }

        BankData db = new BankData();
        int status = 0;
        try {
            db.connect(dbUrl, dbUser, dbPw);
            status = bankAccounts.getOperationStatus(db, key, requestId);
        } catch (SQLException e) {
            responseObserver.onError(UNKNOWN.withDescription("checkStatus: Error connecting to database.").asRuntimeException());
            printError("audit", "DB error - " + e.getMessage());
            return;
        } finally {
            db.closeConnection();
        }

        Status resStatus = Status.newBuilder().setStatus(status).build();
        PrivateKey serverKey = CryptoHelper.readRSAPrivateKey(CryptoHelper.private_path + "/server.priv");

        String signature = CryptoHelper.encodeToBase64(CryptoHelper.signMessage(serverKey,resStatus.toByteArray()));

        checkStatusResponse response = checkStatusResponse.newBuilder().setStatus(resStatus).setSignature(signature).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getNonce(nonceRequest request, StreamObserver<nonceResponse> responseObserver) {
        PublicKey key = CryptoHelper.publicKeyFromBase64(request.getKey());
        String nonce = CryptoHelper.generateNonce();

        pendingRequests.put(key, nonce);

        nonceResponse response = nonceResponse.newBuilder().setNonce(nonce).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private void printError(String function, String message) {
        System.out.println(function + ": " + message);
    }
}
