package pt.tecnico.BFTB.bank.exceptions;

public class AccountAlreadyExistsException extends Exception {
    private static final long serialVersionUID = 1L;

    public AccountAlreadyExistsException(String key) {
        super("Account with key " + key + " already exists");
    }
    
}
