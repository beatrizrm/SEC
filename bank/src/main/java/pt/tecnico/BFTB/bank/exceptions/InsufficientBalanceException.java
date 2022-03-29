package pt.tecnico.BFTB.bank.exceptions;

public class InsufficientBalanceException extends Exception {
    private static final long serialVersionUID = 1L;

    public InsufficientBalanceException() {
        super("Insufficient balance");
    }

    public InsufficientBalanceException(String key) {
        super("Account with key " + key + " has an insufficient balance");
    }
    
}
