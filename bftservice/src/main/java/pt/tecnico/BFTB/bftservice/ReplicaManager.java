package pt.tecnico.BFTB.bftservice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import pt.tecnico.BFTB.bank.grpc.*;

import pt.tecnico.BFTB.bftservice.pojos.Replica;

public class ReplicaManager {
    private int numReplicas;
    private List<Replica> replicas;
    private Replica writeRep;
    
    private int timeoutMs;

    public ReplicaManager(String bankHost, int bankPort, int f) {

        this.numReplicas = 3*f + 1;
        this.replicas = new ArrayList<>(numReplicas);
        this.writeRep = new Replica(bankHost, bankPort, 0,true);
        replicas.add(writeRep);
        System.out.printf("Replica %d at %s:%d\n", 0, bankHost, bankPort);

        for (int i = 1; i < numReplicas; i++) {
            replicas.add(new Replica(bankHost, bankPort+i, i,false));
            System.out.printf("Replica %d at %s:%d\n", i, bankHost, bankPort+i);
        }
        this.timeoutMs = 3000;
    }

    public openAccountResponse openAccount(openAccountRequest request) {
        openAccountResponse response = null;
        for (Replica replica : replicas) {
            response = openAccountRequest(replica, request);
            System.out.println("Replica " + replica.getInstance() + " response: " + response);
        }
        return response;
    }

    public sendAmountResponse sendAmount(sendAmountRequest request) {
        sendAmountResponse response = null;
        for (Replica replica : replicas) {
            response = sendAmountRequest(replica, request);
            System.out.println("Replica " + replica.getInstance() + " response: " + response);
        }
        return response;
    }


    public checkAccountResponse checkAccount(checkAccountRequest request) {
        checkAccountResponse response = null;
        for (Replica replica : replicas) {
            response = checkAccountRequest(replica, request);
            System.out.println("Replica " + replica.getInstance() + " response: " + response);
        }
        return response;
    }

    public checkAccountWriteResponse checkAccountWrite(checkAccountWriteRequest request) {
        checkAccountWriteResponse response = null;
        for (Replica replica : replicas) {
            response = checkAccountWriteRequest(replica, request);
            System.out.println("Replica " + replica.getInstance() + " response: " + response);
        }
        return response;
    }

    public receiveAmountResponse receiveAmount(receiveAmountRequest request) {
        receiveAmountResponse response = null;
        for (Replica replica : replicas) {
            response = receiveAmountRequest(replica, request);
            System.out.println("Replica " + replica.getInstance() + " response: " + response);
        }
        return response;
    }

    public auditResponse audit(auditRequest request) {
        auditResponse response = null;
        for (Replica replica : replicas) {
            response = auditRequest(replica, request);
            System.out.println("Replica " + replica.getInstance() + " response: " + response);
        }
        return response;
    }

    public auditWriteResponse auditWrite(auditWriteRequest request) {
        auditWriteResponse response = null;
        for (Replica replica : replicas) {
            response = auditWriteRequest(replica, request);
            System.out.println("Replica " + replica.getInstance() + " response: " + response);
        }
        return response;
    }

    public checkStatusResponse checkStatus(checkStatusRequest request) {
        checkStatusResponse response = null;
        for (Replica replica : replicas) {
            response = checkStatusRequest(replica, request);
            System.out.println("Replica " + replica.getInstance() + " response: " + response);
        }
        return response;
    }

    public void terminateService() {    // FIXME
        for (Replica replica : replicas) {
            replica.closeChannel();
        }
    }

    //GETTERS E SETTERS

    public int getNumReplicas() {
        return numReplicas;
    }

    public void setNumReplicas(int numReplicas) {
        this.numReplicas = numReplicas;
    }

    public List<Replica> getReplicas() {
        return replicas;
    }

    public void setReplicas(List<Replica> replicas) {
        this.replicas = replicas;
    }

    public int getTimeoutMs() {
        return timeoutMs;
    }

    public void setTimeoutMs(int timeoutMs) {
        this.timeoutMs = timeoutMs;
    }

    public Replica getWriteRep() { return this.writeRep; }




    // ----------------------------------------------------------------------------------------------------------------


    private openAccountResponse openAccountRequest(Replica replica, openAccountRequest request) throws RuntimeException {
        openAccountResponse response = replica.getStub().withDeadlineAfter(timeoutMs, TimeUnit.MILLISECONDS).openAccount(request);
        return response;
    }

    private sendAmountResponse sendAmountRequest(Replica replica, sendAmountRequest request) throws RuntimeException {
        sendAmountResponse response = replica.getStub().withDeadlineAfter(timeoutMs, TimeUnit.MILLISECONDS).sendAmount(request);
        return response;
    }

    private checkAccountResponse checkAccountRequest(Replica replica, checkAccountRequest request) throws RuntimeException {
        checkAccountResponse response = replica.getStub().withDeadlineAfter(timeoutMs, TimeUnit.MILLISECONDS).checkAccount(request);
        return response;
    }

    private checkAccountWriteResponse checkAccountWriteRequest(Replica replica, checkAccountWriteRequest request) throws RuntimeException {
        checkAccountWriteResponse response = replica.getStub().withDeadlineAfter(timeoutMs, TimeUnit.MILLISECONDS).checkAccountWrite(request);
        return response;
    }

    private receiveAmountResponse receiveAmountRequest(Replica replica, receiveAmountRequest request) throws RuntimeException {
        receiveAmountResponse response = replica.getStub().withDeadlineAfter(timeoutMs, TimeUnit.MILLISECONDS).receiveAmount(request);
        return response;
    }

    private auditResponse auditRequest(Replica replica, auditRequest request) throws RuntimeException {
        auditResponse response = replica.getStub().withDeadlineAfter(timeoutMs, TimeUnit.MILLISECONDS).audit(request);
        return response;
    }

    private auditWriteResponse auditWriteRequest(Replica replica, auditWriteRequest request) throws RuntimeException {
        auditWriteResponse response = replica.getStub().withDeadlineAfter(timeoutMs, TimeUnit.MILLISECONDS).auditWrite(request);
        return response;
    }

    private checkStatusResponse checkStatusRequest(Replica replica, checkStatusRequest request) throws RuntimeException {
        checkStatusResponse response = replica.getStub().withDeadlineAfter(timeoutMs, TimeUnit.MILLISECONDS).checkStatus(request);
        return response;
    }
}
