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
import pt.tecnico.BFTB.bank.grpc.checkAccountRequest;
import pt.tecnico.BFTB.bank.grpc.checkAccountResponse;
import pt.tecnico.BFTB.bank.grpc.openAccountRequest;
import pt.tecnico.BFTB.bank.grpc.openAccountResponse;

import java.security.KeyPair;

/**
 *
 * Class to test the balance request of Hub Service
 *
 */
public class CheckAccountIT extends BaseIT {

    private BFTFrontend frontend;

    public CheckAccountIT() {
        this.frontend = new BFTFrontend();
    }

    @Test
    public void checkAccountOKTest() {
        KeyPair keyPairDiogo = CryptoHelper.generate_RSA_keyPair();

        openAccountRequest openAccRequest = openAccountRequest.newBuilder()
                .setUser("Diogo")
                .setKey(CryptoHelper.encodeToBase64(keyPairDiogo.getPublic().getEncoded())).build();
        frontend.openAccount(openAccRequest);

        checkAccountRequest request = checkAccountRequest.newBuilder()
                .setKey(CryptoHelper.encodeToBase64(keyPairDiogo.getPublic().getEncoded()))
                .setRid(0).build();
        checkAccountResponse response = frontend.checkAccount(request);
        assertEquals(500, Integer.parseInt(response.getMsgResponse().getBalance()));
    }
    // Sends an invalid userKeyPair and an exception is raised
    @Test
    public void checkAccountNOKTest() {
        KeyPair keyPairDiogo = CryptoHelper.generate_RSA_keyPair();

        openAccountRequest openAccRequest = openAccountRequest.newBuilder()
                .setUser("Diogo")
                .setKey(CryptoHelper.encodeToBase64(keyPairDiogo.getPublic().getEncoded())).build();
        frontend.openAccount(openAccRequest);

        checkAccountRequest request = checkAccountRequest.newBuilder()
                .setKey("dsfdgdfgdfgdfgdfgs")
                .setRid(0).build();
        assertEquals(INVALID_ARGUMENT.getCode(), assertThrows(StatusRuntimeException.class, () -> frontend.checkAccount(request)).getStatus().getCode());
    }
}