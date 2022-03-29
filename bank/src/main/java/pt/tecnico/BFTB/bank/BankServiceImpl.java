package pt.tecnico.BFTB.bank;

import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;

import pt.tecnico.BFTB.bank.crypto.CryptoHelper;
import pt.tecnico.BFTB.bank.grpc.*;
import pt.tecnico.BFTB.bank.pojos.Transaction;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static io.grpc.Status.INVALID_ARGUMENT;

public class BankServiceImpl extends BankServiceGrpc.BankServiceImplBase{

    private  BankManager bankAccounts;
    private int transactionId;

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
            responseObserver.onCompleted();
        }

        int status = bankAccounts.openAccount(user,key);

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

        //verify signature
        if (!CryptoHelper.verifySignature(msg.toByteArray(),request.getSignature(),key_source)) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("sendAmount: Signature not verified").asRuntimeException());
            responseObserver.onCompleted();
        }

        String key_destination = msg.getDestination();
        String amount = msg.getAmount();
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

        if (key_source == null) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("sendAmount: Source Key cannot be empty!").asRuntimeException());
            responseObserver.onCompleted();
        }

        if (key_destiny == null) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("sendAmount: Destination Key cannot be empty!").asRuntimeException());
            responseObserver.onCompleted();
        }

        if (amount == null || amount.isBlank() || Integer.parseInt(amount) <= 0) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("sendAmount: Amount cannot be empty!").asRuntimeException());
            responseObserver.onCompleted();
        }

        int status = bankAccounts.checkIfTransactionPossible(key_source, Integer.parseInt(amount));

        Transaction transactionSend = new Transaction(transactionId, key_source, key_destiny,0, Integer.parseInt(amount), 0, timeStamp);
        Transaction transactionReceive = new Transaction(transactionId, key_source, key_destiny,1, Integer.parseInt(amount), 0, timeStamp);
        transactionId++;

        bankAccounts.addTransactionHistory(key_source, transactionSend);
        bankAccounts.addTransactionHistory(key_destiny, transactionReceive);

        //set message status and sign it
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
            responseObserver.onCompleted();
        }

        String balance = String.valueOf(bankAccounts.checkBalance(key));

        List<Transaction> transactionHistory = bankAccounts.checkTransactions(key);

        String pendingTransactions = "";

        for(Transaction temp: transactionHistory){
            if(temp.getStatus() == 0){
                pendingTransactions += temp.toString();
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

        //verify signature
        if (!CryptoHelper.verifySignature(msg.toByteArray(),request.getSignature(),CryptoHelper.publicKeyFromBase64(key))) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("sendAmount: Signature not verified").asRuntimeException());
            responseObserver.onCompleted();
        }

        if (key == null || key.isBlank()) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("receiveAmount: Client Key cannot be empty!").asRuntimeException());
            responseObserver.onCompleted();
        }

        if (transactionID == null || transactionID.isBlank()) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("receiveAmount: TransactionId cannot be empty!").asRuntimeException());
            responseObserver.onCompleted();
        }



        int status = bankAccounts.receiveAmount(CryptoHelper.publicKeyFromBase64(key), transactionID);

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
                    INVALID_ARGUMENT.withDescription("checkAccount: Client Key cannot be empty!").asRuntimeException());
            responseObserver.onCompleted();
        }

        List<Transaction> transactions = bankAccounts.checkTransactions(key);

        String transactionHistory = "";

        for(Transaction temp: transactions){
            transactionHistory += temp.toString();
        }

        //sign the message with server private key
        PrivateKey serverKey = CryptoHelper.readRSAPrivateKey(CryptoHelper.private_path + "/server.priv");
        String signature = CryptoHelper.encodeToBase64(CryptoHelper.signMessage(serverKey,transactionHistory.getBytes(StandardCharsets.UTF_8)));

        auditResponse response = auditResponse.newBuilder().setTransactionHistory(transactionHistory).setSignature(signature).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }
}
