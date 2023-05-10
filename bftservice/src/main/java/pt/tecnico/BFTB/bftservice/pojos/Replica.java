package pt.tecnico.BFTB.bftservice.pojos;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import pt.tecnico.BFTB.bank.grpc.BankServiceGrpc;
import pt.tecnico.BFTB.bank.grpc.sendAmountRequest;

import java.util.List;

public class Replica {
    private final int instance;
    private String host;
    private int port;
    private ManagedChannel channel;
    private BankServiceGrpc.BankServiceBlockingStub stub;
    private boolean isWriteReplica;

    public Replica(String host, int port, int instance, boolean isWriteReplica) {

        if (host == null || host.isBlank()) {
            throw new RuntimeException("Hostname is empty");
        }
        if (port < 0) {
            throw new RuntimeException("Invalid Port");
        }
        this.host = host;
        this.port = port;
        this.instance = instance;
        //this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        // this.stub = BankServiceGrpc.newBlockingStub(channel);
        this.isWriteReplica = isWriteReplica;
    }

    public int getInstance() {
        return instance;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public BankServiceGrpc.BankServiceBlockingStub getStub() {
        return stub;
    }

    public void sendAmountConsensus(sendAmountRequest request, List<ManagedChannel> channels) {

            this.stub.sendAmount(request);

    }

    public boolean isWriteReplica() {
        return isWriteReplica;
    }

    public void closeChannel() {
        channel.shutdown();
    }

    public void changeTarget(String host, int port) {
        closeChannel();
        this.host = host;
        this.port = port;
        this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        this.stub = BankServiceGrpc.newBlockingStub(channel);
    }
}