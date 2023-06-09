package pt.tecnico.BFTB.bftservice;

import static io.grpc.Status.INVALID_ARGUMENT;
import static io.grpc.Status.NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.grpc.StatusRuntimeException;
import pt.tecnico.BFTB.bank.BaseIT;
import pt.tecnico.BFTB.bank.crypto.CryptoHelper;
import pt.tecnico.BFTB.bank.grpc.*;

import java.security.KeyPair;
import java.util.UUID;

/**
 *
 * Class to test the audit request of Hub Service
 *
 */

public class AuditIT extends BaseIT {

    private BFTFrontend frontend;

    public AuditIT() {
        this.frontend = new BFTFrontend();
    }

    @Test
    public void AuditOKTest() {
        KeyPair keyPairDiogo = CryptoHelper.generate_RSA_keyPair();

        openAccountRequest openAccRequest = openAccountRequest.newBuilder().setRequestId(UUID.randomUUID().toString()).setKey(CryptoHelper.encodeToBase64(keyPairDiogo.getPublic().getEncoded())).setUser("Diogo").build();
        frontend.openAccount(openAccRequest);

        auditRequest request = auditRequest.newBuilder()
                .setKey(CryptoHelper.encodeToBase64(keyPairDiogo.getPublic().getEncoded()))
                .setRid("0").build();
        auditResponse response = frontend.audit(request);
        assertEquals("", response.getMsg().getTransactionHistory());
    }
    // Sends an invalid userKeyPair and an exception is raised
    @Test
    public void AuditNOKTest() {
        KeyPair keyPairDiogo = CryptoHelper.generate_RSA_keyPair();

        openAccountRequest openAccRequest = openAccountRequest.newBuilder().setRequestId(UUID.randomUUID().toString()).setKey(CryptoHelper.encodeToBase64(keyPairDiogo.getPublic().getEncoded())).setUser("Diogo").build();
        frontend.openAccount(openAccRequest);

        auditRequest request = auditRequest.newBuilder()
                .setKey("dsfdgdfgdfgdfgdfgs258340t3094g053jg034jgetgjdfgjdkjlfghndçjhçksfhtksrfdkhdfklhdlkhlkfsdhlkdhglkdklhdfkljh4t94hy0hr0hbr0h0rhr0gofghlksfhlkjfdhlkjdhfhkljdfkjlhdkljfhlkdfhlkdfhlkdfhldkfhlkjdfhildfhldifht4h49jhflihg")
                .setRid("0").build();
        assertEquals(INVALID_ARGUMENT.getCode(), assertThrows(StatusRuntimeException.class, () -> frontend.audit(request)).getStatus().getCode());
    }
}