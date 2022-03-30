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
    public synchronized int checkBalance(BankData db, PublicKey key) throws SQLException, AccountDoesntExistException {
        String keyb64 = CryptoHelper.encodeToBase64(key.getEncoded());
        return db.getBalance(keyb64);
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
        String srcb64 = CryptoHelper.encodeToBase64(source.getEncoded());
        String dstb64 = CryptoHelper.encodeToBase64(destination.getEncoded());
        if (db.getBalance(srcb64) < amount) {
            throw new InsufficientBalanceException(srcb64);
        }
        if (!db.checkIfAccountExists(srcb64)) {
            throw new AccountDoesntExistException(srcb64);
        }
        if (!db.checkIfAccountExists(dstb64)) {
            throw new AccountDoesntExistException(dstb64);
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
    public synchronized void openAccount(BankData db, String user, PublicKey key) throws SQLException, AccountAlreadyExistsException {
        String keyb64 = CryptoHelper.encodeToBase64(key.getEncoded());
        db.createAccount(keyb64, user, 500);
    }

    /**
     * Returns the value of the balance of the bankAccount mapped by {@code key}
     *
     * @param key
     * @return The value of the balance of the bankAccount with key {@code key}
     * @throws SQLException
     * @throws AccountDoesntExistException
     */
    public synchronized List<Transaction> checkTransactions(BankData db, PublicKey key) throws SQLException, AccountDoesntExistException {
        String keyb64 = CryptoHelper.encodeToBase64(key.getEncoded());
        if (!db.checkIfAccountExists(keyb64)) {
            throw new AccountDoesntExistException(keyb64);
        }
        return db.getTransactionHistory(keyb64);
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
    public synchronized void checkIfCanReceive(BankData db, PublicKey key, Transaction transaction) throws SQLException, 
            AccountDoesntExistException, TransactionAlreadyCompletedException, AccountPermissionException, InsufficientBalanceException {
        if (transaction.getStatus() != 0) {
            throw new TransactionAlreadyCompletedException(transaction.getId());
        }
        if (transaction.getDestination() != CryptoHelper.encodeToBase64(key.getEncoded())) {
            throw new AccountPermissionException();
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
    public synchronized int receiveAmount(BankData db, PublicKey key, String transactionId) throws SQLException,
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
        int transactionId = db.addTransaction(transaction);
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
    public synchronized void addAmount(BankData db, PublicKey key, String amount) throws SQLException, AccountDoesntExistException {
        db.confirmDeposit(CryptoHelper.encodeToBase64(key.getEncoded()), Integer.parseInt(amount));
    }
}
