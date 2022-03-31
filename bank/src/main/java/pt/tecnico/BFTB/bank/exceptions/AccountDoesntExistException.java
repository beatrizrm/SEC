package pt.tecnico.BFTB.bank.exceptions;

public class AccountDoesntExistException extends Exception {
    private static final long serialVersionUID = 1L;

    public AccountDoesntExistException(String key) {
        super("Account with key " + key + " doesn't exist");
    }
    
}
