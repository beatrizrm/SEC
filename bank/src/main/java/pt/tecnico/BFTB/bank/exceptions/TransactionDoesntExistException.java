package pt.tecnico.BFTB.bank.exceptions;

public class TransactionDoesntExistException extends Exception {
    private static final long serialVersionUID = 1L;

    public TransactionDoesntExistException(int id) {
        super("Transaction with id " + id + " not found");
    }
    
}
