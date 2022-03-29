package pt.tecnico.BFTB.bank.exceptions;

public class AccountPermissionException extends Exception {
    private static final long serialVersionUID = 1L;

    public AccountPermissionException() {
        super();
    }
    
    public AccountPermissionException(String message) {
        super(message);
    }
}
