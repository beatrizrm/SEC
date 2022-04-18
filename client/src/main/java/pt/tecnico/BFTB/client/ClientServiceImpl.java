package pt.tecnico.BFTB.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.Status.Code;
import pt.tecnico.BFTB.bank.grpc.*;
import pt.tecnico.BFTB.bank.grpc.BankServiceGrpc.BankServiceBlockingStub;
import pt.tecnico.BFTB.client.crypto.CryptoHelper;
import pt.tecnico.BFTB.client.pojos.Replica;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ClientServiceImpl {
    //    private String user;
    
    private Library lib;
    private KeyPair clientKeys;
    private int numReplicas;
    private List<Replica> replicas;

    // Initialize all inner variables and checks their correctness
    public ClientServiceImpl(int numReplicas) {
        this.lib = new Library();
        this.clientKeys = null;

        this.numReplicas = numReplicas;
        for (int i = 0; i < numReplicas; i++) {
            replicas.add(new Replica("localhost", 8080+i, i));
        }
    }

    public void set_ClientKeys(KeyPair keys) {
        if (keys == null) {
            throw new RuntimeException("KeyPair is empty");
        }
        this.clientKeys = keys;
        lib.setClientKeys(clientKeys);
    }

    private int log_in(String _user) throws FileNotFoundException {

        int status = 1;

        if(this.clientKeys != null) {
            return 0;
        }

        if(!CryptoHelper.checkIfAccountExists(_user)) {
            status = 0;
            throw new FileNotFoundException("There isnt an account with that name");
        }

        //set the client keys
        set_ClientKeys(CryptoHelper.get_keyPair(_user));

        return status;
    }

    private int prepareOpenAccountRequest(String _user) throws RuntimeException, IOException {
        // already logged in an account
        if(this.clientKeys != null) {
            return 2;
        }
        // check if there is an account with that name already
        if(CryptoHelper.checkIfAccountExists(_user)) {
            return 3;
        }
        //gera as chaves do cliente
        set_ClientKeys(CryptoHelper.generate_RSA_keyPair());

        //guardar a chave publica num PKI e guardar a privada respetivamente
        CryptoHelper.SaveKeyPair(_user, clientKeys);

        UUID reqId = UUID.randomUUID();
        int status = -1;

        for (Replica replica : replicas) {
            status = lib.openAccountRequest(replica, _user, reqId);
            System.out.println("Replica " + replica.getInstance() + " response: " + status);
        }
        return status;
    }

    // Sends a balance request and returns the balance (CHECK)
    private String prepareAuditRequest(String _userKey) throws RuntimeException {
        String response = "Audit request failed";
        for (Replica replica : replicas) {
            response = lib.auditRequest(replica, _userKey);
            System.out.println("Replica " + replica.getInstance() + " response: " + response);
        }
        return response;
    }

    private int prepareReceiveAmountRequest(String _userKey, String _transactionId) throws RuntimeException, IOException {
        UUID reqId = UUID.randomUUID();
        int status = -1;

        for (Replica replica : replicas) {
            status = lib.receiveAmountRequest(replica, _userKey, _transactionId, reqId);
            System.out.println("Replica " + replica.getInstance() + " response: " + status);
        } 
        return status;
    }

    private String prepareCheckAccountRequest(String _userKey) {
        String response = "CheckAccount request failed";
        for (Replica replica : replicas) {
            response = lib.checkAccountRequest(replica, _userKey);
            System.out.println("Replica " + replica.getInstance() + " response: " + response);
        }
        return response;
    }

    private int prepareSendAmountRequest(String amount, String source, String dest) throws RuntimeException, IOException {
        UUID reqId = UUID.randomUUID();
        int status = -1;

        for (Replica replica : replicas) {
            status = lib.sendAmountRequest(replica, amount, source, dest, reqId);
            System.out.println("Replica " + replica.getInstance() + " response: " + status);
        }
        return status;
    }
    
    private int checkOperationStatus(UUID reqId) throws RuntimeException {
        /* FIXME!!
        String key = CryptoHelper.encodeToBase64(clientKeys.getPublic().getEncoded());

        checkStatusRequest request = checkStatusRequest.newBuilder().setRequestId(reqId.toString()).setKey(key).build();
        checkStatusResponse response = _stub.withDeadlineAfter(timeoutMs, TimeUnit.MILLISECONDS).checkStatus(request);

        if(!CryptoHelper.verifySignature(response.getStatus().toByteArray(),response.getSignature(),server_pubkey)) {
            throw new RuntimeException("Invalid signature");
        }
        return response.getStatus().getStatus();
        */
        return 50;
    }

    // Sends a terminates the service by closing the channels of all replicas
    public void terminateService() {
        for (Replica replica : replicas) {
            replica.closeChannel();
        }
    }

    // Performs the commands given in the system in or command file
    public String performCommand(String[] args) {
        String response = "OK";
        try {
            switch (args[0]) {
                case "login":
                    if(args.length != 2)
                        throw new RuntimeException("Invalid command open_account");
                    int status0 = this.log_in(args[1]);
                    if(status0 == 1){
                        response = "Logged in sucess";
                    } else {
                        response = "Already logged in";
                    }
                    break;
                case "open_account":
                    if (args.length != 2)
                        throw new RuntimeException("Invalid command open_account");
                    int status = this.prepareOpenAccountRequest(args[1]);
                    System.out.println(status);
                    if(status == 1){
                        response = "OK";
                    } else if (status == -1) {
                        response = "Couldn't confirm if operation was completed";
                    }  else if(status == 2) {
                        response = "Already logged in";
                     } else if(status == 3) {
                        response = "An account with that name already exists";
                    }   else {
                            response = "Couldn't open account";
                        }

                    break;
                case "send_amount":
                    if (args.length != 4)
                        throw new RuntimeException("Invalid command send_amount");
                    int status2 = prepareSendAmountRequest(args[1], args[2], args[3]);
                    if(status2 == 1){
                        response = "OK";
                    } else if (status2 == -1) {
                        response = "Couldn't confirm if operation was completed";
                    } else {
                        response = "Couldn't send amount";
                    }
                    break;
                case "check_account":
                    if (args.length != 2)
                        throw new RuntimeException("Invalid command check_account");
                    response = prepareCheckAccountRequest(args[1]);
                    break;
                case "receive_amount":
                    if (args.length != 3) {
                        throw new RuntimeException("Invalid command receive_amount");
                    }
                    int status3 = prepareReceiveAmountRequest(args[1], args[2]);
                    if(status3 == 1){
                        response = "OK";
                    } else if (status3 == -1) {
                        response = "Couldn't confirm if operation was completed";
                    } else {
                        response = "Couldn't receive amount";
                    }
                    break;
                case "audit":
                    if (args.length != 2)
                        throw new RuntimeException("Invalid command audit");
                    response = prepareAuditRequest(args[1]);
                    break;
                case "exit":
                    if (args.length != 1)
                        throw new RuntimeException("Invalid command exit");
                    response = "Client Stopped";
                    break;
                default:
                    response = "Invalid command";
                    break;
            }
        } catch (RuntimeException | IOException e) {
            response = "ERRO " + e.getMessage();
        }
        return response;
    }

    // Opens a resource file in the src/main/resource folder
    public BufferedReader openResourcesFile(String filePath) throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(filePath)).getFile());
        return new BufferedReader(new FileReader(file));

    }
}

