package pt.tecnico.BFTB.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import pt.tecnico.BFTB.bank.grpc.*;
import pt.tecnico.BFTB.bank.grpc.BankServiceGrpc.BankServiceBlockingStub;
import pt.tecnico.BFTB.client.crypto.CryptoHelper;

import java.io.*;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Objects;

public class ClientServiceImpl {

    private String _BankHost;
    private int _BankPort;
    private ManagedChannel _channel;
    private BankServiceBlockingStub _stub;
    private KeyPair clientKeys;
    private String user;


    // Initialize all inner variables and checks their correctness
    public ClientServiceImpl(String[] args) {
        this.set_BankHost("localhost");
        this.set_BankPort("8080");
        _channel = ManagedChannelBuilder.forAddress(_BankHost, _BankPort).usePlaintext().build();
        _stub = BankServiceGrpc.newBlockingStub(_channel);
    }

    // Checks if the hub host name is valid and saves it
    public void set_BankHost(String bankHost) {
        if (bankHost == null || bankHost.isBlank()) {
            throw new RuntimeException("Hostname is empty");
        }
        _BankHost = bankHost;
    }

    // Checks if the hub host port is valid and saves it
    public void set_BankPort(String bankPort) {
        int portInt = Integer.parseInt(bankPort);
        if (portInt < 0) {
            throw new RuntimeException("Invalid Port");
        }
        _BankPort = portInt;
    }

    public String get_BankHost() {
        return _BankHost;
    }

    public int get_BankPort() {
        return _BankPort;
    }

    // Sends a balance request and returns the balance
    private int openAccountRequest(String _user) throws RuntimeException, IOException {

        //gera as chaves do cliente
        clientKeys = CryptoHelper.generate_RSA_keyPair();
        user = _user;

        //guardar a chave publica num PKI e guardar a privada respetivamente
        CryptoHelper.SaveKeyPair(user,clientKeys);

        //chave publica (passar para Base64)
        PublicKey client_pubkey = clientKeys.getPublic();
        String key = CryptoHelper.encodeToBase64(client_pubkey.getEncoded());

        openAccountRequest request = openAccountRequest.newBuilder().setKey(key).setUser(_user).build();
        openAccountResponse response = _stub.openAccount(request);
        return response.getStatus();

    }

    // Sends a balance request and returns the balance
    private String auditRequest(String _userKey) throws RuntimeException {
        auditRequest request = auditRequest.newBuilder().setKey(_userKey).build();
        auditResponse response = _stub.audit(request);
        return response.getTransactionHistory();
    }

    // Sends a balance request and returns the balance
    private int receiveAmountRequest(String _userKey, String _transactionId) throws RuntimeException {
        receiveAmountRequest request = receiveAmountRequest.newBuilder().setKey(_userKey).setTransactionId(_transactionId).build();
        receiveAmountResponse response = _stub.receiveAmount(request);
        return response.getStatus();
    }

    // Sends a balance request and returns the balance
    private String checkAccountRequest(String _userKey) throws RuntimeException {
        checkAccountRequest request = checkAccountRequest.newBuilder().setKey(_userKey).build();
        checkAccountResponse response = _stub.checkAccount(request);
        return response.getBalance() + response.getPendingTransactions();
    }

    // Sends an amount request and returns the balance
    private int sendAmountRequest(String amount, String source, String dest) throws RuntimeException {

        int amountInt = Integer.parseInt(amount);
        if (amountInt < 0)
            throw new RuntimeException("Invalid amount to send");

        // get source and dest key
        String source_key = CryptoHelper.encodeToBase64(clientKeys.getPublic().getEncoded());
        PublicKey dst_key = CryptoHelper.readRSAPublicKey(CryptoHelper.pki_path + "/" + dest + ".pub");
        String destiny_key = CryptoHelper.encodeToBase64(dst_key.getEncoded());

        //create the msg and signature
        sendAmountContent msg = sendAmountContent.newBuilder().setAmount(amount).setDestination(destiny_key).setSource(source_key).build();
        String signature = CryptoHelper.encodeToBase64(CryptoHelper.signMessage(clientKeys.getPrivate(),msg.toByteArray()));


        sendAmountRequest request = sendAmountRequest.newBuilder().setMessage(msg).setSignature(signature).build();
        sendAmountResponse response = _stub.sendAmount(request);
        return response.getStatus();


    }

    // Sends a terminates the service by closing the channel
    public void terminateService() {
        _channel.shutdownNow();
    }

    // Performs the commands given in the system in or command file
    public String performCommand(String[] args) {
        String response = "OK";
        try {
            switch (args[0]) {
                case "open_account":
                    if (args.length != 2)
                        throw new RuntimeException("Invalid command open_account");
                    int status = this.openAccountRequest(args[1]);
                    if(status == 1){
                        response = "OK";
                    } else {
                        response = "Couldn't open account";
                    }
                    break;
                case "send_amount":
                    if (args.length != 4)
                        throw new RuntimeException("Invalid command send_amount");
                    int status2 = sendAmountRequest(args[1], args[2], args[3]);
                    if(status2 == 1){
                        response = "OK";
                    } else {
                        response = "Couldn't send amount";
                    }
                    break;
                case "check_account":
                    if (args.length != 2)
                        throw new RuntimeException("Invalid command check_account");
                    response = checkAccountRequest(args[1]);
                    break;
                case "receive_amount":
                    if (args.length != 3) {
                        throw new RuntimeException("Invalid command receive_amount");
                    }
                    int status3 = receiveAmountRequest(args[1], args[2]);
                    if(status3 == 1){
                        response = "OK";
                    } else {
                        response = "Couldn't receive amount";
                    }
                    break;
                case "audit":
                    if (args.length != 2)
                        throw new RuntimeException("Invalid command audit");
                    response = auditRequest(args[1]);
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

