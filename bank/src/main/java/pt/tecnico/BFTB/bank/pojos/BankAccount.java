package pt.tecnico.BFTB.bank.pojos;

import java.io.Serial;
import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class BankAccount implements Serializable {


    @Serial
    private static final long serialVersionUID = 5199339552272503330L;

    private int balance;
    private String user;
    private PublicKey pubKey;
    private List<Transaction> transactionsHistory;

    public BankAccount(String user,PublicKey pubKey, int balance) {

        this.user = user;
        this.pubKey = pubKey;
        this.balance = balance;
        this.transactionsHistory = new ArrayList<>();

    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionsHistory;
    }

    public void setTransactionsHistory(List<Transaction> transactionsHistory) {
        this.transactionsHistory = transactionsHistory;
    }

    public void addTransaction(Transaction transaction){
        transactionsHistory.add(transaction);
    }

    public void confirm_deposit(int amount){
        if(amount != 0){
            this.balance += amount;
        }
    }

    public void confirm_withdrawal(int amount){
        if(amount != 0){
            this.balance -= amount;
        }
    }

    public void printTransactionsHistory(List<Transaction> transactionsHistory) {
        for (Transaction transaction : transactionsHistory) {
            if(transaction.getStatus() == 1){
                System.out.println(transaction.toString());
            }
        }
    }

    public void printNotConfirmedTransactionsHistory(List<Transaction> transactionsHistory) {
        for (Transaction transaction : transactionsHistory) {
            if(transaction.getStatus() == 0){
                System.out.println(transaction.toString());
            }
        }
    }
}
