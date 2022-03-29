package pt.tecnico.BFTB.bank;

import pt.tecnico.BFTB.bank.exceptions.AccountAlreadyExistsException;
import pt.tecnico.BFTB.bank.exceptions.AccountDoesntExistException;
import pt.tecnico.BFTB.bank.exceptions.AccountPermissionException;
import pt.tecnico.BFTB.bank.exceptions.InsufficientBalanceException;
import pt.tecnico.BFTB.bank.exceptions.TransactionAlreadyCompletedException;
import pt.tecnico.BFTB.bank.exceptions.TransactionDoesntExistException;
import pt.tecnico.BFTB.bank.grpc.Bank;
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
     * @throws InsufficientBalanceException
     */
    public synchronized void checkIfTransactionPossible(BankData db, PublicKey source, PublicKey destination, int amount) throws SQLException, AccountDoesntExistException, InsufficientBalanceException {
        String srcB64 = CryptoHelper.encodeToBase64(source.getEncoded());
        String dstB64 = CryptoHelper.encodeToBase64(destination.getEncoded());
        if (db.getBalance(srcB64) < amount) {
            throw new InsufficientBalanceException(srcB64);
        }
        if (!db.checkIfAccountExists(srcB64)) {
            throw new AccountDoesntExistException(srcB64);
        }
        if (!db.checkIfAccountExists(dstB64)) {
            throw new AccountDoesntExistException(dstB64);
        }
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
     * @throws AccountDoesntExistException
     */
    public synchronized List<Transaction> checkTransactions(BankData db, String key) throws SQLException, AccountDoesntExistException {
        if (!db.checkIfAccountExists(key)) {
            throw new AccountDoesntExistException(key);
        }
        return db.getTransactionHistory(key);
    }

    /**
     * Returns the value of the balance of the bankAccount mapped by {@code key}
     *
     * @param key
     * @return The value of the balance of the bankAccount with key {@code key}
     * @throws SQLException
     * @throws AccountDoesntExistException
     * @throws TransactionAlreadyCompletedException
     * @throws AccountPermissionException
     * @throws InsufficientBalanceException
     */
    public synchronized void checkIfCanReceive(BankData db, String key, Transaction transaction) throws SQLException, 
            AccountDoesntExistException, TransactionAlreadyCompletedException, AccountPermissionException, InsufficientBalanceException {
        if (transaction.getStatus() != 0) {
            throw new TransactionAlreadyCompletedException(transaction.getId());
        }
        if (transaction.getDestination() != key) {
            throw new AccountPermissionException("hi");
        }
        if (transaction.getAmount() > db.getBalance(transaction.getSource())) {
            throw new InsufficientBalanceException();
        }
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
     * @throws InsufficientBalanceException
     * @throws AccountPermissionException
     * @throws TransactionAlreadyCompletedException
     */
    public synchronized int receiveAmount(BankData db, String key, String transactionId) throws SQLException,
            TransactionDoesntExistException, AccountDoesntExistException, TransactionAlreadyCompletedException, AccountPermissionException, InsufficientBalanceException {
        Transaction transaction = db.getTransactionDetails(Integer.parseInt(transactionId));
        checkIfCanReceive(db, key, transaction);
        db.confirmTransaction(Integer.parseInt(transactionId));
        db.confirmWithdrawal(transaction.getSource(), transaction.getAmount());
        db.confirmDeposit(transaction.getDestination(), transaction.getAmount());
        return 1;
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
