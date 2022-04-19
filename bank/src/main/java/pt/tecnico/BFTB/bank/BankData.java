package pt.tecnico.BFTB.bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pt.tecnico.BFTB.bank.crypto.CryptoHelper;
import pt.tecnico.BFTB.bank.exceptions.AccountAlreadyExistsException;
import pt.tecnico.BFTB.bank.exceptions.AccountDoesntExistException;
import pt.tecnico.BFTB.bank.exceptions.NonceAlreadyExistsException;
import pt.tecnico.BFTB.bank.exceptions.TransactionDoesntExistException;
import pt.tecnico.BFTB.bank.pojos.BankAccount;
import pt.tecnico.BFTB.bank.pojos.Transaction;

public class BankData {
    private Connection db;

    public void connect(String url, String user, String password) throws SQLException {
        db = DriverManager.getConnection(url, user, password);
    }

    public void closeConnection() {
        if (db != null) {
            try {
                db.close();
            } catch (SQLException e) {
                System.out.println("Error closing database connection: " + e.getMessage());
            }
        }
    }

    public void beginTransaction() throws SQLException {
        db.setAutoCommit(false);
    }

    public void commit() throws SQLException {
        db.commit();
        db.setAutoCommit(true);
    }

    public void rollback() {
        try {
            db.rollback();
            db.setAutoCommit(true);
        } catch(SQLException e) {
            System.out.println("Error during transaction rollback: " + e.getMessage());
        }
    }

    public void createAccount(String key, String user, int balance) throws SQLException, AccountAlreadyExistsException {
        try (PreparedStatement ps = db.prepareStatement("INSERT INTO account VALUES (?, ?, ?) ON CONFLICT DO NOTHING")) {
            ps.setString(1, key);
            ps.setString(2, user);
            ps.setInt(3, balance);
            if (ps.executeUpdate() == 0) {
                throw new AccountAlreadyExistsException(key);
            }
        }
    }

    public BankAccount getAccountDetails(String key) throws SQLException, AccountDoesntExistException {
        try (PreparedStatement ps = db.prepareStatement("SELECT * FROM account WHERE public_key = ?")) {
            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new BankAccount(
                    rs.getString(2),                                    // user
                    CryptoHelper.publicKeyFromBase64(rs.getString(1)),  // public key
                    rs.getInt(3)                                        // balance
                );
            }
            else {
                throw new AccountDoesntExistException(key);
            }
        }
    }

    public boolean checkIfAccountExists(String key) throws SQLException {
        try (PreparedStatement ps = db.prepareStatement("SELECT public_key FROM account WHERE public_key = ?")) {
            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
            return false;
        }
    }

    public int getBalance(String key) throws SQLException, AccountDoesntExistException {
        try (PreparedStatement ps = db.prepareStatement("SELECT balance FROM account WHERE public_key = ?")) {
            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int balance = rs.getInt(1);
                return balance;
            }
            else {
                throw new AccountDoesntExistException(key);
            }
        }
    }

    public void confirmDeposit(String key, int amount) throws SQLException, AccountDoesntExistException {
        if (amount == 0) {
            return;
        }
        try (PreparedStatement ps = db.prepareStatement("UPDATE account SET balance = balance + ? WHERE public_key = ?")) {
            ps.setInt(1, amount);
            ps.setString(2, key);
            if (ps.executeUpdate() == 0) {
                throw new AccountDoesntExistException(key);
            }
        }
    }

    public void confirmWithdrawal(String key, int amount) throws SQLException, AccountDoesntExistException {
        if (amount == 0) {
            return;
        }
        try (PreparedStatement ps = db.prepareStatement("UPDATE account SET balance = balance - ? WHERE public_key = ?")) {
            ps.setInt(1, amount);
            ps.setString(2, key);
            if (ps.executeUpdate() == 0) {
                throw new AccountDoesntExistException(key);
            }
        }
    }

    public int addTransaction(Transaction transaction, String srcSignature) throws SQLException {
        try (PreparedStatement ps = db.prepareStatement("INSERT INTO transaction_info VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)", 
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, transaction.getSource());
            ps.setString(2, transaction.getDestination());
            ps.setInt(3, transaction.getAmount());
            ps.setInt(4, transaction.getStatus());
            ps.setString(5, transaction.getTimeStamp());
            ps.setString(6, srcSignature);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                return id;
            }
            return 0;
        }
    }

    public void addTransactionToHistory(String key, int transactionId, int sign) throws SQLException {
        try (PreparedStatement ps = db.prepareStatement("INSERT INTO transaction_history VALUES (?, ?, ?)")) {
            ps.setString(1, key);
            ps.setInt(2, transactionId);
            ps.setInt(3, sign);
            ps.executeUpdate();
        }
    }

    public Transaction getTransactionDetails(int transactionId) throws SQLException, TransactionDoesntExistException {
        try (PreparedStatement ps = db.prepareStatement("SELECT * FROM transaction_info WHERE transaction_id = ?")) {
            ps.setInt(1, transactionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Transaction(
                    rs.getInt(1),       // id
                    rs.getString(2),    // source
                    rs.getString(3),    // destination
                    0,                  // sign - not used
                    rs.getInt(4),       // amount
                    rs.getInt(5),       // status
                    rs.getString(6)     // timestamp
                    );
            }
            else {
                throw new TransactionDoesntExistException(transactionId);
            }
        }
    }

    public void confirmTransaction(int transactionId, String dstSignature) throws SQLException, TransactionDoesntExistException {
        try (PreparedStatement ps = db.prepareStatement("UPDATE transaction_info SET status = 1, dst_signature = ? WHERE transaction_id = ?")) {
            ps.setString(1, dstSignature);
            ps.setInt(2, transactionId);
            if (ps.executeUpdate() == 0) {
                throw new TransactionDoesntExistException(transactionId);
            }
        };
    }

    public List<Transaction> getTransactionHistory(String key) throws SQLException {
        try (PreparedStatement ps = db.prepareStatement("SELECT th.transaction_id, ti.source, src.username as sourceuser, "
                + "ti.destination, dst.username as destinationUser, th.sign, ti.amount, ti.status, ti.ts FROM transaction_history th "
                + "JOIN transaction_info ti ON th.transaction_id = ti.transaction_id JOIN account src ON src.public_key = ti.source "
                + "JOIN account dst ON dst.public_key = ti.destination AND th.public_key = ? ORDER BY th.transaction_id")) {    
            List<Transaction> transactions = new ArrayList<Transaction>();
            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction trans = new Transaction(
                    rs.getInt(1),       // id
                    rs.getString(3),    // source
                    rs.getString(5),    // destination
                    rs.getInt(6),       // sign
                    rs.getInt(7),       // amount
                    rs.getInt(8),       // status
                    rs.getString(9)     // timestamp
                );
                transactions.add(trans);
            }
            return transactions;  
        }
    }

    public void setOperationStatus(String key, String requestId, int status) throws SQLException {
        try (PreparedStatement ps = db.prepareStatement("INSERT INTO operation_log VALUES (?, ?, ?)"
                + "ON CONFLICT (public_key, request_id) DO UPDATE SET status = ?")) {
            ps.setString(1, key);
            ps.setString(2, requestId);
            ps.setInt(3, status);
            ps.setInt(4, status);
            ps.executeUpdate();
        }
    }

    public int getOperationStatus(String key, String requestId) throws SQLException {
        try (PreparedStatement ps = db.prepareStatement("SELECT status FROM operation_log WHERE (public_key, request_id) = (?, ?)")) {
            ps.setString(1, key);
            ps.setString(2, requestId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    public void addNonce(String nonce) throws SQLException, NonceAlreadyExistsException {
        try (PreparedStatement ps = db.prepareStatement("INSERT INTO used_nonces VALUES (?) ON CONFLICT DO NOTHING")) {
            ps.setString(1, nonce);
            if (ps.executeUpdate() == 0) {
                throw new NonceAlreadyExistsException();
            }
        }
    }
}
