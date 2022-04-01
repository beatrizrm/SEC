package pt.tecnico.BFTB.bank.exceptions;

public class TransactionAlreadyCompletedException extends Exception {
    private static final long serialVersionUID = 1L;

    public TransactionAlreadyCompletedException(int transactionId) {
        super("Transaction with id " + transactionId + " has already been completed");
    }
    
}
