package pt.tecnico.BFTB.bank.exceptions;

public class NonceAlreadyExistsException extends Exception{
    private static final long serialVersionUID = 1L;
    
    public NonceAlreadyExistsException() {
        super("Nonce has already been used");
    }
}
