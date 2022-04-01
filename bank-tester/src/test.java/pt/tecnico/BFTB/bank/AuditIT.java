package pt.tecnico.BFTB.bank;

import static io.grpc.Status.INVALID_ARGUMENT;
import static io.grpc.Status.NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.grpc.StatusRuntimeException;
import pt.tecnico.BFTB.bank.crypto.CryptoHelper;
import pt.tecnico.BFTB.bank.grpc.*;

import java.security.KeyPair;

/**
 *
 * Class to test the balance request of Hub Service
 *
 */
public class AuditIT extends BaseIT {

    private BankFrontend frontend;

    public AuditIT() {
        this.frontend = new BankFrontend();
    }

    @Test
    public void AuditOKTest() {
        KeyPair keyPairDiogo = CryptoHelper.generate_RSA_keyPair();

        openAccountRequest openAccRequest = openAccountRequest.newBuilder().setUser("Diogo").setKey(CryptoHelper.encodeToBase64(keyPairDiogo.getPublic().getEncoded())).build();
        openAccountResponse openAccResponse = frontend.openAccount(openAccRequest);

        auditRequest request = auditRequest.newBuilder().setKey(CryptoHelper.encodeToBase64(keyPairDiogo.getPublic().getEncoded())).build();
        auditResponse response = frontend.audit(request);
        assertEquals("", response.getTransactionHistory());
    }
    // Sends an invalid userKeyPair and an exception is raised
    @Test
    public void AuditNOKTest() {
        KeyPair keyPairDiogo = CryptoHelper.generate_RSA_keyPair();

        openAccountRequest openAccRequest = openAccountRequest.newBuilder().setUser("Diogo").setKey(CryptoHelper.encodeToBase64(keyPairDiogo.getPublic().getEncoded())).build();
        openAccountResponse openAccResponse = frontend.openAccount(openAccRequest);

        auditRequest request = auditRequest.newBuilder().setKey("dsfdgdfgdfgdfgdfgs").build();
        auditResponse response = frontend.audit(request);
        assertEquals(NOT_FOUND.getCode(), assertThrows(StatusRuntimeException.class, () -> frontend.audit(request)).getStatus().getCode());
    }
}