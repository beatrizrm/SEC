package pt.tecnico.BFTB.bank;

import io.grpc.stub.StreamObserver;

import pt.tecnico.BFTB.bank.grpc.*;
import pt.tecnico.BFTB.bank.pojos.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static io.grpc.Status.INVALID_ARGUMENT;

public class BankServiceImpl extends BankServiceGrpc.BankServiceImplBase{

    private  BankManager bankAccounts;

    public BankServiceImpl() {
        this.bankAccounts = new BankManager();
    }

    @Override
    public void readBalance(BankReadBalanceRequest request, StreamObserver<BankReadBalanceResponse> responseObserver) {
        String key = request.getKey();

        if (key == null || key.isBlank()) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("ReadBalance: Client Key cannot be empty!").asRuntimeException());
            responseObserver.onCompleted();
        }

        String balance = String.valueOf(bankAccounts.readBankAccountBalance(key));

        BankReadBalanceResponse response = BankReadBalanceResponse.newBuilder().setBalance(balance).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void readTransactionHistory(BankReadTransactionHistoryRequest request, StreamObserver<BankReadTransactionHistoryResponse> responseObserver) {
        String key = request.getKey();
        int status = request.getStatus();

        if (key == null || key.isBlank()) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("ReadTransactionHistory: Client Key cannot be empty!").asRuntimeException());
            responseObserver.onCompleted();
        }

        List<Transaction> transactionHistory = bankAccounts.readBankAccountTransactionHistory(key);

        String transactionsHistory = "";

        for(Transaction temp: transactionHistory){
            if(temp.getStatus() == status){
                transactionsHistory += temp.toString();
            }
        }


        BankReadTransactionHistoryResponse response = BankReadTransactionHistoryResponse.newBuilder().setTransactionsHistory(transactionsHistory).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void writeBalance(BankWriteBalanceRequest request, StreamObserver<BankWriteBalanceResponse> responseObserver) {
        String key = request.getKey();
        String amount = request.getAmount();

        // Throws exception if key is null or is an empty string
        if (key == null || key.isBlank()) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("WriteBalance: Client Key cannot be empty!").asRuntimeException());
            responseObserver.onCompleted();
        }

        // Throws exception if value is null
        if (amount == null) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("WriteBalance: Amount cannot be null!").asRuntimeException());
            responseObserver.onCompleted();
        }

        // Adds the amount
        bankAccounts.addAmount(key, amount);


        String output = "Balance updated with Success!";
        BankWriteBalanceResponse response = BankWriteBalanceResponse.newBuilder().setStatus(output).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void writeTransactionHistory(BankWriteTransactionHistoryRequest request, StreamObserver<BankWriteTransactionHistoryResponse> responseObserver) {
        String key = request.getKey();
        String amount = request.getTransactionAmount();
        String flag = request.getFlag();
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

        // Throws exception if key is null or is an empty string
        if (key == null || key.isBlank()) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("WriteTransactionHistory: Client Key cannot be empty!").asRuntimeException());
            responseObserver.onCompleted();
        }

        // Throws exception if value is null
        if (amount == null) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("WriteTransactionHistory: Amount cannot be null!").asRuntimeException());
            responseObserver.onCompleted();
        }

        Transaction transaction = new Transaction(Integer.parseInt(flag), Integer.parseInt(amount), 0, timeStamp);

        // Adds the not confirmed transaction
        bankAccounts.addTransactionHistory(key, transaction);

        String output = "Transaction saved with Success!";
        BankWriteTransactionHistoryResponse response = BankWriteTransactionHistoryResponse.newBuilder().setStatus(output).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
