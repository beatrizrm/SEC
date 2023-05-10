package pt.tecnico.BFTB.bftservice;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import pt.tecnico.BFTB.bank.grpc.*;

public class BFTFrontend {

    private ManagedChannel channel;
    private BFTServiceGrpc.BFTServiceBlockingStub stub;

    public BFTFrontend(){
        this.channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();

        this.stub = BFTServiceGrpc.newBlockingStub(channel);
    }

    public openAccountResponse openAccount(openAccountRequest request){
        return stub.openAccount(request);
    }

    public sendAmountResponse sendAmount(sendAmountRequest request){
        return stub.sendAmount(request);
    }

    public checkAccountResponse checkAccount(checkAccountRequest request){
        return stub.checkAccount(request);
    }

    public receiveAmountResponse receiveAmount(receiveAmountRequest request){
        return stub.receiveAmount(request);
    }

    public auditResponse audit(auditRequest request){
        return stub.audit(request);
    }

    public void disconnect() {
        channel.shutdown();
    }
}
