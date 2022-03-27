package pt.tecnico.BFTB.bank;

import pt.tecnico.BFTB.bank.exceptions.AccountAlreadyExistsException;
import pt.tecnico.BFTB.bank.exceptions.AccountDoesntExistException;
import pt.tecnico.BFTB.bank.exceptions.TransactionDoesntExistException;
import pt.tecnico.BFTB.bank.crypto.CryptoHelper;
import pt.tecnico.BFTB.bank.pojos.Transaction;

import java.io.IOException;
import java.security.KeyPair;
import java.security.PublicKey;
import java.sql.SQLException;
import java.util.List;

public class BankManager {

    private KeyPair serverKeys;

    public BankManager() throws IOException {

        this.serverKeys = CryptoHelper.generate_RSA_keyPair();
        CryptoHelper.SaveKeyPair("server",serverKeys);

    }


    /**
     * Returns the value of the balance of the bankAccount mapped by {@code key}
     *
     * @param key
     * @return The value of the balance of the bankAccount with key {@code key}
     * @throws SQLException
     * @throws AccountDoesntExistException
     */
    public synchronized int checkBalance(BankData db, String key) throws SQLException, AccountDoesntExistException {
        return db.getBalance(key);
    }

    /**
     * Returns the value of the balance of the bankAccount mapped by {@code key}
     *
     * @param key
     * @return The value of the balance of the bankAccount with key {@code key}
     * @throws SQLException
     * @throws AccountDoesntExistException
     */
    public synchronized int checkIfTransactionPossible(BankData db, PublicKey key, int amount) throws SQLException, AccountDoesntExistException {
        int status = 0;
        String keyB64 = CryptoHelper.encodeToBase64(key.getEncoded());
        if (db.getBalance(keyB64) > amount) {
            status = 1;
        }
        return status;
    }

    /**
     * Returns the value of the balance of the bankAccount mapped by {@code key}
     *
     * @param key
     * @return The value of the balance of the bankAccount with key {@code key}
     * @throws SQLException
     * @throws AccountAlreadyExistsException
     */
    public synchronized void openAccount(BankData db, String user, String key) throws SQLException, AccountAlreadyExistsException {
        //get Pubkey
        CryptoHelper.decodeFromBase64(key);
        PublicKey client_pubkey = CryptoHelper.publicKeyFromBase64(key);
        System.out.println(key);
        db.createAccount(key, 500);
    }

    /**
     * Returns the value of the balance of the bankAccount mapped by {@code key}
     *
     * @param key
     * @return The value of the balance of the bankAccount with key {@code key}
     * @throws SQLException
     */
    public synchronized List<Transaction> checkTransactions(BankData db, String key) throws SQLException {
        return db.getTransactionHistory(key);
    }

    /**
     * Returns the value of the balance of the bankAccount mapped by {@code key}
     *
     * @param key
     * @param transactionId
     * @return The value of the balance of the bankAccount with key {@code key}
     * @throws SQLException
     * @throws TransactionDoesntExistException
     * @throws AccountDoesntExistException
     */
    public synchronized int receiveAmount(BankData db, String key, String transactionId) throws SQLException,
            TransactionDoesntExistException, AccountDoesntExistException {
        int status = 0;
        Transaction transaction = db.getTransactionDetails(Integer.parseInt(transactionId));
        if (transaction.getAmount() < db.getBalance(transaction.getSource())) {
            db.confirmTransaction(Integer.parseInt(transactionId));
            db.confirmWithdrawal(transaction.getSource(), transaction.getAmount());
            db.confirmDeposit(transaction.getDestination(), transaction.getAmount());
            status = 1;
        }
        return status;
    }

    /**
     * Adds a transaction to the bankAccount repository, mapped by {@code key}
     *
     * @param key
     * @param transaction
     * @throws SQLException
     */
    public synchronized void addTransactionHistory(BankData db, Transaction transaction) throws SQLException {
        int transactionId = db.addTransaction(transaction); // check 0?
        db.addTransactionToHistory(transaction.getSource(), transactionId, 0);
        db.addTransactionToHistory(transaction.getDestination(), transactionId, 1);
    }

    /**
     * Adds an amount to the specific bankAccount repository, mapped by {@code key}
     *
     * @param key
     * @param amount
     * @throws SQLException
     * @throws AccountDoesntExistException
     */
    public synchronized void addAmount(BankData db, String key, String amount) throws SQLException, AccountDoesntExistException {
        db.confirmDeposit(key, Integer.parseInt(amount));
    }
}
