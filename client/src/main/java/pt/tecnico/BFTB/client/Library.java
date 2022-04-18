package pt.tecnico.BFTB.client;

import pt.tecnico.BFTB.bank.grpc.*;
import pt.tecnico.BFTB.bank.grpc.BankServiceGrpc.BankServiceBlockingStub;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import pt.tecnico.BFTB.client.crypto.CryptoHelper;
import pt.tecnico.BFTB.client.pojos.Replica;

public class Library {
    private KeyPair clientKeys;
    private PublicKey server_pubkey;
    private int timeoutMs;

    public Library() {
        this.server_pubkey = CryptoHelper.readRSAPublicKey(CryptoHelper.pki_path + "/server.pub");
        this.timeoutMs = 300;
    }

    public KeyPair getClientKeys() {
        return clientKeys;
    }
    public void setClientKeys(KeyPair clientKeys) {
        this.clientKeys = clientKeys;
    }
    public PublicKey getServerKey() {
        return server_pubkey;
    }
    public void setServerKey(PublicKey server_pubkey) {
        this.server_pubkey = server_pubkey;
    }
    public int getTimeoutMs() {
        return timeoutMs;
    }
    public void setTimeoutMs(int timeoutMs) {
        this.timeoutMs = timeoutMs;
    }

    public int openAccountRequest(Replica replica, String _user, UUID reqId) throws RuntimeException, IOException {
        //chave publica (passar para Base64)
        PublicKey client_pubkey = clientKeys.getPublic();
        String key = CryptoHelper.encodeToBase64(client_pubkey.getEncoded());

        openAccountRequest request = openAccountRequest.newBuilder().setRequestId(reqId.toString()).setKey(key).setUser(_user).build();
        openAccountResponse response = replica.getStub().withDeadlineAfter(timeoutMs, TimeUnit.MILLISECONDS).openAccount(request);

        System.out.println("open account function server key: " + server_pubkey.toString());

        //verify server signature
        if(!CryptoHelper.verifySignature(response.getStatus().toByteArray(),response.getSignature(),server_pubkey)) {
            throw new RuntimeException("Invalid signature");
        }

        return response.getStatus().getStatus();
    }

    public String auditRequest(Replica replica, String _userKey) throws RuntimeException {
        //get the pretend pubKey
        PublicKey key = CryptoHelper.readRSAPublicKey(CryptoHelper.pki_path+"/" + _userKey + ".pub");
        String key_string = CryptoHelper.encodeToBase64(key.getEncoded());

        auditRequest request = auditRequest.newBuilder().setKey(key_string).build();
        auditResponse response = replica.getStub().withDeadlineAfter(timeoutMs, TimeUnit.MILLISECONDS).audit(request);

        //verify server signature
        if(!CryptoHelper.verifySignature(response.getTransactionHistory().getBytes(StandardCharsets.UTF_8),response.getSignature(),server_pubkey)) {
            throw new RuntimeException("Invalid signature");
        }

        return response.getTransactionHistory();

    }

    public int receiveAmountRequest(Replica replica, String _userKey, String _transactionId, UUID reqId) throws RuntimeException {
        //get the pretend pubKey
        PublicKey key = CryptoHelper.readRSAPublicKey(CryptoHelper.pki_path+"/" + _userKey + ".pub");
        String key_string = CryptoHelper.encodeToBase64(key.getEncoded());

        //construct the message
        receiveAmountContent msg = receiveAmountContent.newBuilder()
                .setRequestId(reqId.toString()).setKey(key_string).setTransactionId(_transactionId).build();

        //sign the message
        String signature = CryptoHelper.encodeToBase64(CryptoHelper.signMessage(clientKeys.getPrivate(),msg.toByteArray()));

        receiveAmountRequest request = receiveAmountRequest.newBuilder().setMsg(msg).setSignature(signature).build();
        receiveAmountResponse response = replica.getStub().withDeadlineAfter(timeoutMs, TimeUnit.MILLISECONDS).receiveAmount(request);

        //verify server signature
        if(!CryptoHelper.verifySignature(response.getStatus().toByteArray(),response.getSignature(),server_pubkey)) {
            throw new RuntimeException("Invalid signature");
        }

        return response.getStatus().getStatus();

    }

    public String checkAccountRequest(Replica replica, String _userKey) throws RuntimeException {
        //get the pretend pubKey
        PublicKey key = CryptoHelper.readRSAPublicKey(CryptoHelper.pki_path+"/" + _userKey + ".pub");
        String key_string = CryptoHelper.encodeToBase64(key.getEncoded());

        checkAccountRequest request = checkAccountRequest.newBuilder().setKey(key_string).build();
        checkAccountResponse response = replica.getStub().withDeadlineAfter(timeoutMs, TimeUnit.MILLISECONDS).checkAccount(request);

        checkAccountResMsg msg = response.getMsgResponse();

        //verify server signature
        if(!CryptoHelper.verifySignature(msg.toByteArray(),response.getSignature(),server_pubkey)) {
            throw new RuntimeException("Invalid signature");
        }

        return msg.getBalance() + msg.getPendingTransactions();
    }

    public int sendAmountRequest(Replica replica, String amount, String source, String dest, UUID reqId) throws RuntimeException {
        int amountInt = Integer.parseInt(amount);
        if (amountInt < 0)
            throw new RuntimeException("Invalid amount to send");

        // get source and dest key
        String source_key = CryptoHelper.encodeToBase64(clientKeys.getPublic().getEncoded());
        PublicKey dst_key = CryptoHelper.readRSAPublicKey(CryptoHelper.pki_path + "/" + dest + ".pub");
        String destiny_key = CryptoHelper.encodeToBase64(dst_key.getEncoded());

        //create the msg and signature
        sendAmountContent msg = sendAmountContent.newBuilder().setRequestId(reqId.toString()).setAmount(amount)
                                                    .setDestination(destiny_key).setSource(source_key).build();
        String signature = CryptoHelper.encodeToBase64(CryptoHelper.signMessage(clientKeys.getPrivate(),msg.toByteArray()));

        //send the request for the server
        sendAmountRequest request = sendAmountRequest.newBuilder().setMessage(msg).setSignature(signature).build();
        sendAmountResponse response = replica.getStub().withDeadlineAfter(timeoutMs, TimeUnit.MILLISECONDS).sendAmount(request);

        //verify server signature
        if(!CryptoHelper.verifySignature(response.getStatus().toByteArray(),response.getSignature(),server_pubkey)) {
            throw new RuntimeException("Invalid signature");
        }

        return response.getStatus().getStatus();
    }
}
