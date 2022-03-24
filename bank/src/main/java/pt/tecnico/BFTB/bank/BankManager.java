package pt.tecnico.BFTB.bank;

import pt.tecnico.BFTB.bank.pojos.BankAccount;
import pt.tecnico.BFTB.bank.pojos.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankManager {

    private Map<String, BankAccount> bankAccounts;

    public BankManager() {
        this.bankAccounts = new HashMap<String, BankAccount>();
    }

    /**
     * Returns the value of the balance of the bankAccount mapped by {@code key}
     *
     * @param key
     * @return The value of the balance of the bankAccount with key {@code key}
     */
    public synchronized int readBankAccountBalance(String key) {
        BankAccount bankAccount = bankAccounts.get(key);
        return bankAccount.getBalance();
    }

    /**
     * Returns the value of the balance of the bankAccount mapped by {@code key}
     *
     * @param key
     * @return The value of the balance of the bankAccount with key {@code key}
     */
    public synchronized List<Transaction> readBankAccountTransactionHistory(String key) {
        BankAccount bankAccount = bankAccounts.get(key);
        return bankAccount.getTransactionHistory();
    }


    /**
     * Adds a transaction to the bankAccount repository, mapped by {@code key}
     *
     * @param key
     * @param transaction
     */
    public synchronized void addTransactionHistory(String key, Transaction transaction) {
        BankAccount bankAccount = bankAccounts.get(key);
        bankAccount.addTransaction(transaction);

    }

    /**
     * Adds an amount to the specific bankAccount repository, mapped by {@code key}
     *
     * @param key
     * @param amount
     */
    public synchronized void addAmount(String key, String amount) {
        BankAccount bankAccount = bankAccounts.get(key);
        bankAccount.confirm_deposit(Integer.parseInt(amount));
    }
}
