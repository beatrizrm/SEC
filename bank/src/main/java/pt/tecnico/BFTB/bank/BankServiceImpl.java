package pt.tecnico.BFTB.bank;

import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import pt.tecnico.BFTB.bank.exceptions.AccountAlreadyExistsException;
import pt.tecnico.BFTB.bank.exceptions.AccountDoesntExistException;
import pt.tecnico.BFTB.bank.exceptions.TransactionDoesntExistException;
import pt.tecnico.BFTB.bank.crypto.CryptoHelper;
import pt.tecnico.BFTB.bank.grpc.*;
import pt.tecnico.BFTB.bank.pojos.Transaction;

import java.sql.SQLException;
import java.io.IOException;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static io.grpc.Status.INVALID_ARGUMENT;
import static io.grpc.Status.UNKNOWN;
import static io.grpc.Status.ABORTED;
import static io.grpc.Status.ALREADY_EXISTS;
import static io.grpc.Status.NOT_FOUND;

public class BankServiceImpl extends BankServiceGrpc.BankServiceImplBase{
    String dburl = "jdbc:postgresql:bank"; // FIXME
    String dbuser = "sec";    // FIXME
    String dbpassword ="sec"; // FIXME

    private  BankManager bankAccounts;

    public BankServiceImpl() throws IOException {
        this.bankAccounts = new BankManager();
    }

    @Override
    public void openAccount(openAccountRequest request, StreamObserver<openAccountResponse> responseObserver) {

        String key = request.getKey();
        String user = request.getUser();

        if (key == null || key.isBlank()) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("openAccount: Client Key cannot be empty!").asRuntimeException());
            return;
        }

        BankData db = new BankData();
        int status = 0;
        try {
            db.connect(dburl, dbuser, dbpassword);
            bankAccounts.openAccount(db, user, key);
            status = 1;
        } catch (AccountAlreadyExistsException e) {
            responseObserver.onError(ALREADY_EXISTS.withDescription("openAccount: " + e.getMessage()).asRuntimeException());
            return;
        } catch (SQLException e) {
            responseObserver.onError(UNKNOWN.withDescription("openAccount: Error connecting to database.").asRuntimeException());
            printError("openAccount", "DB error - " + e.getMessage());
            return;
        } finally {
            db.closeConnection();
        }

        openAccountResponse response = openAccountResponse.newBuilder().setStatus(status).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void sendAmount(sendAmountRequest request, StreamObserver<sendAmountResponse> responseObserver) {

        //get message and verify signature
        sendAmountContent msg = request.getMessage();
        PublicKey key_source = CryptoHelper.publicKeyFromBase64(msg.getSource());
        PublicKey key_destiny = CryptoHelper.readRSAPublicKey(msg.getDestination());

        if (!CryptoHelper.verifySignature(msg.toByteArray(),request.getSignature(),key_source)) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("sendAmount: Signature not verified").asRuntimeException());
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

        if (amount == null || amount.isBlank() || Integer.parseInt(amount) <= 0) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("sendAmount: Amount cannot be empty!").asRuntimeException());
            return;
        }

        BankData db = new BankData();
        int status = 0;
        try {
            db.connect(dburl, dbuser, dbpassword);
            db.beginTransaction();
            status = bankAccounts.checkIfTransactionPossible(db, key_source, Integer.parseInt(amount));
            if (status == 1) {
                Transaction transaction = new Transaction(0, msg.getSource(), msg.getDestination(), 0, Integer.parseInt(amount), 0, timeStamp);
                bankAccounts.addTransactionHistory(db, transaction);
            }
            db.commit();
        } catch (AccountDoesntExistException e) {
            responseObserver.onError(NOT_FOUND.withDescription("sendAmount: " + e.getMessage()).asRuntimeException());
            return;
        } catch (SQLException e) {
            responseObserver.onError(ABORTED.withDescription("sendAmount: Error connecting to database.").asRuntimeException());
            printError("sendAmount", "DB error - " + e.getMessage());
            db.rollback();
            return;
        } finally {
            db.closeConnection();
        }

        sendAmountResponse response = sendAmountResponse.newBuilder().setStatus(status).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void checkAccount(checkAccountRequest request, StreamObserver<checkAccountResponse> responseObserver) {
        String key = request.getKey();

        if (key == null || key.isBlank()) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("checkAccount: Client Key cannot be empty!").asRuntimeException());
            return;
        }

        BankData db = new BankData();
        String balance = "";
        List<Transaction> transactionHistory = null;
        try {
            db.connect(dburl, dbuser, dbpassword);
            balance = String.valueOf(bankAccounts.checkBalance(db, key));
            transactionHistory = bankAccounts.checkTransactions(db, key);
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

        for(Transaction temp: transactionHistory){
            if(temp.getStatus() == 0){
                pendingTransactions += temp.toString();
            }
        }

        checkAccountResponse response = checkAccountResponse.newBuilder().setBalance(balance).setPendingTransactions(pendingTransactions).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void receiveAmount(receiveAmountRequest request, StreamObserver<receiveAmountResponse> responseObserver) {
        String key = request.getKey();
        String transactionID = request.getTransactionId();

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
            db.connect(dburl, dbuser, dbpassword);
            db.beginTransaction();
            status = bankAccounts.receiveAmount(db, key, transactionID);
            db.commit();
        } catch (AccountDoesntExistException | TransactionDoesntExistException e) {
            responseObserver.onError(NOT_FOUND.withDescription("receiveAmount: " + e.getMessage()).asRuntimeException());
            return;
        } catch (SQLException e) {
            responseObserver.onError(ABORTED.withDescription("receiveAmount: Error connecting to database.").asRuntimeException());
            printError("receiveAmount", "DB error - " + e.getMessage());
            db.rollback();
            return;
        } finally {
            db.closeConnection();
        }


        receiveAmountResponse response = receiveAmountResponse.newBuilder().setStatus(status).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void audit(auditRequest request, StreamObserver<auditResponse> responseObserver) {
        String key = request.getKey();

        if (key == null || key.isBlank()) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("audit: Client Key cannot be empty!").asRuntimeException());
            responseObserver.onCompleted();
        }

        BankData db = new BankData();
        List<Transaction> transactions = null;
        try {
            db.connect(dburl, dbuser, dbpassword);
            transactions = bankAccounts.checkTransactions(db, key);
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

        auditResponse response = auditResponse.newBuilder().setTransactionHistory(transactionHistory).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private void printError(String function, String message) {
        System.out.println(function + ": " + message);
    }
}
