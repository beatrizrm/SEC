package pt.tecnico.BFTB.bank;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static io.grpc.Status.INVALID_ARGUMENT;
import static io.grpc.Status.FAILED_PRECONDITION;
import static io.grpc.Status.ABORTED;
import static io.grpc.Status.ALREADY_EXISTS;
import static io.grpc.Status.NOT_FOUND;
import static io.grpc.Status.UNKNOWN;
import static io.grpc.Status.PERMISSION_DENIED;

public class BankServiceImpl extends BankServiceGrpc.BankServiceImplBase{
    String dbUrl, dbUser, dbPw;

    private  BankManager bankAccounts;
    private int instanceNumber;


    public BankServiceImpl(String dbName, String dbUser, String dbPw, int instanceNumber, int bankPort) throws IOException {
        this.instanceNumber = instanceNumber;
        this.dbUrl = "jdbc:postgresql://localhost:" + bankPort + "/" + dbName + instanceNumber;
        System.out.println(this.dbUrl);
        this.dbUser = dbUser;
        this.dbPw = dbPw;
        this.bankAccounts = new BankManager();
    }

    public int getInstanceNumber() {
        return instanceNumber;
    }

    public void setInstanceNumber(int instanceNumber) {
        this.instanceNumber = instanceNumber;
    }

    @Override
    public void openAccount(openAccountRequest request, StreamObserver<openAccountResponse> responseObserver) {

        String key = request.getMsg().getKey();
        String user = request.getMsg().getUser();
        String requestId = request.getMsg().getRequestId();

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
            bankAccounts.setRegister(db,key,0,"");
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

        System.out.println("estou aqui para mandar a resposta");
        openAccountResponse response = openAccountResponse.newBuilder().setStatus(status1).setSignature(signature).build();
        System.out.println(response.toString());
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void sendAmount(sendAmountRequest request, StreamObserver<sendAmountResponse> responseObserver) {

        BankData db = new BankData();

        //consensus

        if(request.getTimestampSource() == -1 && request.getTimestampDest() == -1) {
            sendAmountExecute(request,responseObserver,true);
        }else {
            sendAmountExecute(request,responseObserver,false);
        }

    }

    public void sendAmountExecute(sendAmountRequest request,StreamObserver<sendAmountResponse> responseObserver,boolean isWriter) {

        System.out.println("dentro da funcao sendAmountexecute");
        //get message and verify signature
        sendAmountContent msg = request.getMessage();
        PublicKey key_source = CryptoHelper.publicKeyFromBase64(msg.getSource());
        PublicKey key_destiny = CryptoHelper.publicKeyFromBase64(msg.getDestination());
        String requestId = msg.getRequestId();

        //verify signature
        if (!CryptoHelper.verifySignature(msg.toByteArray(), request.getSignature(), key_source)) {
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

        int wts_source = -1;
        int wts_dest = -1;

        try {

            db.connect(dbUrl, dbUser, dbPw);
            db.beginTransaction();

            bankAccounts.checkIfTransactionPossible(db, key_source, key_destiny, Integer.parseInt(amount));
            Transaction transaction = new Transaction(request.getNextTransactionId(), msg.getSource(), msg.getDestination(), 0, Integer.parseInt(amount), 0, timeStamp);
            bankAccounts.addTransactionHistory(db, transaction);
            bankAccounts.setOperationStatus(db, msg.getSource(), requestId, 1);

            if(isWriter) {

                System.out.println("dentro do isWriter");
                wts_source = bankAccounts.getRegisterTs(db,msg.getSource());
                wts_dest = bankAccounts.getRegisterTs(db,msg.getDestination());
                bankAccounts.updateRegister(db,msg.getSource(),wts_source+1,"");
                bankAccounts.updateRegister(db,msg.getDestination(),wts_dest+1,"");
                System.out.println(wts_dest);
                System.out.println(wts_source);

            } else {
                System.out.println("dentro do else");
                wts_source = bankAccounts.getRegisterTs(db,msg.getSource());
                wts_dest = bankAccounts.getRegisterTs(db,msg.getDestination());
               bankAccounts.updateRegister(db, msg.getSource(),wts_source+1,"");
               bankAccounts.updateRegister(db,msg.getDestination(),wts_dest+1,"");
            }

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
        String signature = CryptoHelper.encodeToBase64(CryptoHelper.signMessage(serverKey, status1.toByteArray()));

        sendAmountResponse response;
        if (isWriter) {
            System.out.println("wts_source : " + wts_source);
            System.out.println("wts_dest : " + wts_dest);
           response = sendAmountResponse.newBuilder().setStatus(status1).setWtsSource(wts_source+1).setWtsDest(wts_dest+1).setSignature(signature).build();
        } else {
            response = sendAmountResponse.newBuilder().setStatus(status1).setSignature(signature).build();
        }


        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }


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
        int rid = -1;

        try {
            db.connect(dbUrl, dbUser, dbPw);
            balance = String.valueOf(bankAccounts.checkBalance(db, key_string));
            transactionHistory = bankAccounts.checkTransactions(db, key_string);
            rid = bankAccounts.getRegisterTs(db,key_string);

            System.out.println("" + rid);
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
        String allTransactions = "";
        String newline = ""; 
        for(Transaction temp: transactionHistory){
            if(temp.getStatus() == 0){
                pendingTransactions += newline + temp.toString();
                newline = "\n";
            }
            allTransactions +=  temp.toString();
            newline = "\n";
        }


        //create the response message
        checkAccountResMsg msg = checkAccountResMsg.newBuilder().setBalance(balance).setPendingTransactions(pendingTransactions).setAllTransactions(allTransactions).build();

        // sign message so client knows that was server who send it
        PrivateKey serverKey = CryptoHelper.readRSAPrivateKey(CryptoHelper.private_path + "/server.priv");
        String signature = CryptoHelper.encodeToBase64(CryptoHelper.signMessage(serverKey,msg.toByteArray()));


        checkAccountResponse response = checkAccountResponse.newBuilder().setMsgResponse(msg).setSignature(signature).setRid(rid).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void checkAccountWrite(checkAccountWriteRequest request, StreamObserver<checkAccountWriteResponse> responseStreamObserver) {

        //get the current wts for the correspondent account
        BankData db = new BankData();
        try {

            db.connect(dbUrl, dbUser, dbPw);
            int account_wts = db.getRegisterTs(request.getKey());

            //replica nao ta atualizada
            if (request.getWts() > account_wts) {

                String allTransactionsString = request.getMsg().getAllTransactions();
                // ids de todas as transacoes
                List<Integer> allTransacIds = parseTransactionIds(allTransactionsString);

                List<Transaction> transactionHistory = null;
                // transacoes nesta replica
                transactionHistory = db.getTransactionHistory(request.getKey());

                for (Integer id: allTransacIds) {
                    int i = 0;
                    for (Transaction transaction_in_this: transactionHistory) {
                        if (transaction_in_this.getId() == id)
                            i += 1;
                    }

                    // falta transacao com ID id
                    if (i == 0) {
                        //adicionar transacao a DB
                        Transaction transac_to_add = parseStringToTransaction(allTransactionsString,id);
                        db.beginTransaction();
                        bankAccounts.addTransactionHistory(db,transac_to_add);
                        bankAccounts.updateRegister(db,request.getKey(),request.getWts(),"");
                        db.commit();
                    }

                }

            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }


    }

    private List<Integer> parseTransactionIds(String allTransactionsString) {

        List<Integer> retList = new ArrayList<>();
        String[] transactions = allTransactionsString.split("Transaction");
        if (transactions.length > 1) {

            for (int i = 1; i < transactions.length; i++) {
                String[] transac_info = transactions[i].split("/");
                retList.add(Integer.parseInt(transac_info[1]));
            }

        }

        return retList;

    }

    private Transaction parseStringToTransaction(String allTransactionsString, int id) {

        String[] transactions = allTransactionsString.split("Transaction");
        Transaction transaction = null;

            for (int i = 1; i < transactions.length; i++) {
                String[] transac_info = transactions[i].split("/");
                if(Integer.parseInt(transac_info[1]) == id) {

                    int sign;
                    if(Character.compare(transac_info[4].charAt(0),'+') == 0)
                        sign = 1;
                    else
                        sign = 0;

                    int amount = Character.getNumericValue(transac_info[4].charAt(1));

                    transaction = new Transaction(id,CryptoHelper.encodeToBase64(CryptoHelper.get_keyPair(transac_info[2]).getPublic().getEncoded()),CryptoHelper.encodeToBase64(CryptoHelper.get_keyPair(transac_info[3]).getPublic().getEncoded()),sign,amount,Integer.parseInt(transac_info[5]),transac_info[6]);
                }
            }

            return transaction;


    }




    @Override
    public void receiveAmount(receiveAmountRequest request, StreamObserver<receiveAmountResponse> responseObserver) {

        BankData db = new BankData();

        //consensus

        if(request.getTimestampSource() == -1 && request.getTimestampDest() == -1) {
            receiveAmountExecute(request,responseObserver,true);
        }else {
            receiveAmountExecute(request,responseObserver,false);
        }

    }

    public void receiveAmountExecute(receiveAmountRequest request, StreamObserver<receiveAmountResponse> responseObserver, boolean isWriter) {

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

        if (key == null || key.isBlank()) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("receiveAmount: Client Key cannot be empty!").asRuntimeException());
            return;
        }

        if (transactionID == null ) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("receiveAmount: TransactionId cannot be empty!").asRuntimeException());
            return;
        }

        BankData db = new BankData();
        int status = 0;
        int wts_source = -1;
        int wts_dest = -1;

        try {
            db.connect(dbUrl, dbUser, dbPw);
            db.beginTransaction();
            bankAccounts.receiveAmount(db, CryptoHelper.publicKeyFromBase64(key), transactionID);
            bankAccounts.setOperationStatus(db, key, requestId, 1);

            if(isWriter) {
                //get transaction
                Transaction get_trans = bankAccounts.getTransactionDetails(db,Integer.parseInt(msg.getTransactionId()));
                System.out.println("dentro do isWriter");
                wts_source = bankAccounts.getRegisterTs(db,get_trans.getSource());
                wts_dest = bankAccounts.getRegisterTs(db,get_trans.getDestination());
                bankAccounts.updateRegister(db,get_trans.getSource(),wts_source+1,"");
                bankAccounts.updateRegister(db, get_trans.getDestination(), wts_dest+1,"");
                System.out.println(wts_dest);
                System.out.println(wts_source);

            } else {
                System.out.println("dentro do else");
                Transaction get_trans = bankAccounts.getTransactionDetails(db,Integer.parseInt(msg.getTransactionId()));
                wts_source = bankAccounts.getRegisterTs(db,get_trans.getSource());
                wts_dest = bankAccounts.getRegisterTs(db,get_trans.getDestination());
                bankAccounts.updateRegister(db,get_trans.getSource(),wts_source+1,"");
                bankAccounts.updateRegister(db, get_trans.getDestination(), wts_dest+1,"");
            }

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

        receiveAmountResponse response;

        if (isWriter) {
            System.out.println("wts_source : " + wts_source);
            System.out.println("wts_dest : " + wts_dest);
            response = receiveAmountResponse.newBuilder().setStatus(status1).setWtsSource(wts_source+1).setWtsDest(wts_dest+1).setSignature(signature).build();
        } else {
            response = receiveAmountResponse.newBuilder().setStatus(status1).setSignature(signature).build();
        }


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
        int rid = -1;
        try {
            db.connect(dbUrl, dbUser, dbPw);
            transactions = bankAccounts.checkTransactions(db, key_string);
            rid = bankAccounts.getRegisterTs(db,key_string);
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

        auditResponseMsg msg = auditResponseMsg.newBuilder().setTransactionHistory(transactionHistory).setSignature(signature).build();
        auditResponse response = auditResponse.newBuilder().setMsg(msg).setRid(rid).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void auditWrite(auditWriteRequest request, StreamObserver<auditWriteResponse> responseStreamObserver) {

        //get the current wts for the correspondent account
        BankData db = new BankData();
        try {

            db.connect(dbUrl, dbUser, dbPw);
            int account_wts = db.getRegisterTs(request.getKey());

            //replica nao ta atualizada
            if (request.getWts() > account_wts) {

                String allTransactionsString = request.getMsg().getTransactionHistory();
                // ids de todas as transacoes
                List<Integer> allTransacIds = parseTransactionIds(allTransactionsString);

                List<Transaction> transactionHistory = null;
                // transacoes nesta replica
                transactionHistory = db.getTransactionHistory(request.getKey());

                for (Integer id: allTransacIds) {
                    int i = 0;
                    for (Transaction transaction_in_this: transactionHistory) {
                        if (transaction_in_this.getId() == id)
                            i += 1;
                    }

                    // falta transacao com ID id
                    if (i == 0) {
                        //adicionar transacao a DB
                        Transaction transac_to_add = parseStringToTransaction(allTransactionsString,id);
                        db.beginTransaction();
                        bankAccounts.addTransactionHistory(db,transac_to_add);
                        bankAccounts.updateRegister(db,request.getKey(),request.getWts(),"");
                        db.commit();
                    }

                }

            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }


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

    private void printError(String function, String message) {
        System.out.println(function + ": " + message);
    }

    @Override
    public void nonceWrite(nonceWriteRequest request, StreamObserver<nonceWriteResponse> responseObserver) {
        BankData db = new BankData();
        int wts;
        List<String> nonces = null;
        try {
            db.connect(dbUrl, dbUser, dbPw);
            wts = bankAccounts.addNonce(db, request.getNonce(), -1, request.getSignature());
            nonces = bankAccounts.getUsedNonces(db);
        } catch (NonceAlreadyExistsException e) {
            responseObserver.onError(PERMISSION_DENIED.withDescription(e.getMessage()).asRuntimeException());
            return;
        } catch (SQLException e) {
            responseObserver.onError(UNKNOWN.withDescription("nonceWrite: Error connecting to database.").asRuntimeException());
            printError("nonceWrite", "DB error - " + e.getMessage());
            return;
        }
        nonceReg reg = nonceReg.newBuilder().addAllNonces(nonces).setWts(wts).build();
        nonceWriteResponse response = nonceWriteResponse.newBuilder().setNonceRegister(reg).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    
    @Override
    public void nonceWriteBack(nonceWriteBackRequest request, StreamObserver<nonceWriteBackResponse> responseObserver) {
        BankData db = new BankData();
        
        try {
            db.connect(dbUrl, dbUser, dbPw);
        } catch (SQLException e) {
            responseObserver.onError(UNKNOWN.withDescription("nonceWriteBack: Error connecting to database.").asRuntimeException());
            printError("nonceWriteBack", "DB error - " + e.getMessage());
            return;
        }

        int wts = request.getNonceRegister().getWts();
        List<String> nonces = request.getNonceRegister().getNoncesList();
        try {

            for (String nonce : nonces) {
                try {
                    bankAccounts.addNonce(db, nonce, wts, request.getSignature());
                } catch (NonceAlreadyExistsException e) { 
                    // do nothing - copying everything during write back
                }
            }

        } catch (SQLException e) {
            responseObserver.onError(UNKNOWN.withDescription("nonceWriteBack: Error connecting to database.").asRuntimeException());
            printError("nonceWriteBack", "DB error - " + e.getMessage());
            return;
        }

        ACKmessage ack = ACKmessage.newBuilder().setAck("ACK").build();
        nonceWriteBackResponse response = nonceWriteBackResponse.newBuilder().setAck(ack).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void maxTransactionId(maxTransactionIdRequest request, StreamObserver<maxTransactionIdResponse> responseObserver) {
        BankData db = new BankData();
        int id;
        try {
            db.connect(dbUrl, dbUser, dbPw);
            id = db.getMaxTransactionId();
        } catch (SQLException e) {
            responseObserver.onError(UNKNOWN.withDescription("maxTransactionId: Error connecting to database.").asRuntimeException());
            printError("maxTransactionId", "DB error - " + e.getMessage());
            return;
        }
        maxTransactionIdResponse response = maxTransactionIdResponse.newBuilder().setId(id).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
