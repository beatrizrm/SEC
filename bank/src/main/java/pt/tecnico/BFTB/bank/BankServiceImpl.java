package pt.tecnico.BFTB.bank;

import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;

import pt.tecnico.BFTB.bank.crypto.CryptoHelper;
import pt.tecnico.BFTB.bank.grpc.*;
import pt.tecnico.BFTB.bank.pojos.Transaction;

import java.io.IOException;
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

        Transaction transactionSend = new Transaction(transactionId, sourceKey, destinationKey,0, Integer.parseInt(amount), 0, timeStamp);
        Transaction transactionReceive = new Transaction(transactionId, sourceKey, destinationKey,1, Integer.parseInt(amount), 0, timeStamp);
        transactionId++;

        bankAccounts.addTransactionHistory(sourceKey, transactionSend);
        bankAccounts.addTransactionHistory(destinationKey, transactionReceive);

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
            responseObserver.onCompleted();
        }

        if (transactionID == null || transactionID.isBlank()) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("receiveAmount: TransactionId cannot be empty!").asRuntimeException());
            responseObserver.onCompleted();
        }

        int status = bankAccounts.receiveAmount(key, transactionID);


        receiveAmountResponse response = receiveAmountResponse.newBuilder().setStatus(status).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void audit(auditRequest request, StreamObserver<auditResponse> responseObserver) {
        String key = request.getKey();

        if (key == null || key.isBlank()) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("checkAccount: Client Key cannot be empty!").asRuntimeException());
            responseObserver.onCompleted();
        }

        List<Transaction> transactions = bankAccounts.checkTransactions(key);

        String transactionHistory = "";

        for(Transaction temp: transactions){
            transactionHistory += temp.toString();
        }

        auditResponse response = auditResponse.newBuilder().setTransactionHistory(transactionHistory).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
