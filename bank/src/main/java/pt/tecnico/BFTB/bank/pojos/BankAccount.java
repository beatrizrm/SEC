package pt.tecnico.BFTB.bank.pojos;

import java.util.List;

public class BankAccount {

    int balance;
    List<Transaction> transactionsHistory;

    public BankAccount(int balance) {
        this.balance = balance;
        this.transactionsHistory = null;
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
