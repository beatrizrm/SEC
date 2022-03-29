package pt.tecnico.BFTB.bank;

import pt.tecnico.BFTB.bank.crypto.CryptoHelper;
import pt.tecnico.BFTB.bank.pojos.BankAccount;
import pt.tecnico.BFTB.bank.pojos.Transaction;

import java.io.IOException;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankManager {

    private Map<PublicKey, BankAccount> bankAccounts;
    private KeyPair serverKeys;

    public BankManager() throws IOException {

        this.bankAccounts = new HashMap<PublicKey, BankAccount>();
        this.serverKeys = CryptoHelper.generate_RSA_keyPair();
        CryptoHelper.SaveKeyPair("server",serverKeys);
        System.out.println(serverKeys.getPublic().toString());

    }


    /**
     * Returns the value of the balance of the bankAccount mapped by {@code key}
     *
     * @param key
     * @return The value of the balance of the bankAccount with key {@code key}
     */
    public synchronized int checkBalance(PublicKey key) {

        BankAccount bankAccount = bankAccounts.get(key);
        return bankAccount.getBalance();

    }

    /**
     * Returns the value of the balance of the bankAccount mapped by {@code key}
     *
     * @param key
     * @return The value of the balance of the bankAccount with key {@code key}
     */
    public synchronized int checkIfTransactionPossible(PublicKey key, int amount) {

        int status = 0;

        BankAccount bankAccount = bankAccounts.get(key);
        if(bankAccount.getBalance() > amount){
            status = 1;
        }

        return status;
    }

    /**
     * Returns the value of the balance of the bankAccount mapped by {@code key}
     *
     * @param key
     * @return The value of the balance of the bankAccount with key {@code key}
     */
    public synchronized int openAccount(String user, String key) {

        int status = 0;

        //get Pubkey
        CryptoHelper.decodeFromBase64(key);
        PublicKey client_pubkey = CryptoHelper.publicKeyFromBase64(key);
        System.out.println(key);

        if(bankAccounts.get(key) == null){
            BankAccount bankAccount = new BankAccount(user,client_pubkey,500);
            bankAccounts.put(client_pubkey, bankAccount);
            status = 1;
        }
        return status;
    }

    /**
     * Returns the value of the balance of the bankAccount mapped by {@code key}
     *
     * @param key
     * @return The value of the balance of the bankAccount with key {@code key}
     */
    public synchronized List<Transaction> checkTransactions(PublicKey key) {
        BankAccount bankAccount = bankAccounts.get(key);
        return bankAccount.getTransactionHistory();
    }

    /**
     * Returns the value of the balance of the bankAccount mapped by {@code key}
     *
     * @param key
     * @param transactionId
     * @return The value of the balance of the bankAccount with key {@code key}
     */
    public synchronized int receiveAmount(PublicKey key, String transactionId) {

        int status = 0;
        BankAccount destinationBankAccount = bankAccounts.get(key);
        PublicKey sourceKey = null;
        for(Transaction temp: destinationBankAccount.getTransactionHistory()){
            if(temp.getId() == Integer.parseInt(transactionId)){
                sourceKey = temp.getSource();
                BankAccount sourceBankAccount = bankAccounts.get(sourceKey);
                for(Transaction temp2: sourceBankAccount.getTransactionHistory()){
                    if(temp2.getId() == Integer.parseInt(transactionId)){
                        if(temp2.getAmount() < sourceBankAccount.getBalance()){
                            status = 1;
                            temp2.setStatus(1);
                            sourceBankAccount.confirm_withdrawal(temp2.getAmount());
                            break;
                        }
                    }
                }

                temp.setStatus(1);
                destinationBankAccount.confirm_deposit(temp.getAmount());
                break;
            }
        }
        return status;
    }

    /**
     * Adds a transaction to the bankAccount repository, mapped by {@code key}
     *
     * @param key
     * @param transaction
     */
    public synchronized void addTransactionHistory(PublicKey key, Transaction transaction) {
        BankAccount bankAccount = bankAccounts.get(key);
        bankAccount.addTransaction(transaction);

    }

    /**
     * Adds an amount to the specific bankAccount repository, mapped by {@code key}
     *
     * @param key
     * @param amount
     */
    public synchronized void addAmount(PublicKey key, String amount) {
        BankAccount bankAccount = bankAccounts.get(key);
        bankAccount.confirm_deposit(Integer.parseInt(amount));
    }
}
