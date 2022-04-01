package pt.tecnico.BFTB.bank;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import pt.tecnico.BFTB.bank.grpc.*;

public class BankFrontend {

    private ManagedChannel channel;
    private BankServiceGrpc.BankServiceBlockingStub stub;

    public BankFrontend(){
        this.channel = ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext().build();

        this.stub = BankServiceGrpc.newBlockingStub(channel);
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
