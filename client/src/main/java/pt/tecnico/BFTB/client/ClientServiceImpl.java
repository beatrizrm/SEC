package pt.tecnico.BFTB.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import pt.tecnico.BFTB.bank.grpc.BankServiceGrpc;
import pt.tecnico.BFTB.bank.grpc.BankServiceGrpc.BankServiceBlockingStub;
import pt.tecnico.BFTB.bank.grpc.openAccountRequest;
import pt.tecnico.BFTB.bank.grpc.openAccountResponse;
import pt.tecnico.BFTB.bank.grpc.sendAmountRequest;
import pt.tecnico.BFTB.bank.grpc.sendAmountResponse;
import pt.tecnico.BFTB.bank.grpc.checkAccountRequest;
import pt.tecnico.BFTB.bank.grpc.checkAccountResponse;
import pt.tecnico.BFTB.bank.grpc.receiveAmountRequest;
import pt.tecnico.BFTB.bank.grpc.receiveAmountResponse;
import pt.tecnico.BFTB.bank.grpc.auditRequest;
import pt.tecnico.BFTB.bank.grpc.auditResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Objects;

public class ClientServiceImpl {

    private String _BankHost;
    private int _BankPort;
    private ManagedChannel _channel;
    private BankServiceBlockingStub _stub;

    // Initialize all inner variables and checks their correctness
    public ClientServiceImpl(String[] args) {
        this.set_BankHost(args[0]);
        this.set_BankPort(args[1]);
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
    private int openAccountRequest(String _userKey) throws RuntimeException {
        openAccountRequest request = openAccountRequest.newBuilder().setKey(_userKey).build();
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
    private int receiveAmountRequest(String _userKey) throws RuntimeException {
        receiveAmountRequest request = receiveAmountRequest.newBuilder().setKey(_userKey).build();
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

        sendAmountRequest request = sendAmountRequest.newBuilder().setAmount(amount).setSourceKey(source).setDestinationKey(dest).build();
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
                    if (args.length != 2) {
                        throw new RuntimeException("Invalid command receive_amount");
                    }
                    int status3 = receiveAmountRequest(args[1]);
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
        } catch (RuntimeException e) {
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

