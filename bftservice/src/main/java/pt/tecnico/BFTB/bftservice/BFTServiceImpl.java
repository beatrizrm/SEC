package pt.tecnico.BFTB.bftservice;

import io.grpc.Context;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import pt.tecnico.BFTB.bank.grpc.*;
import pt.tecnico.BFTB.bftservice.crypto.CryptoHelper;
import pt.tecnico.BFTB.bftservice.pojos.Replica;
import pt.tecnico.BFTB.bftservice.tools.BFTLogger;
import pt.tecnico.BFTB.bftservice.tools.RepStreamObserver;
import pt.tecnico.BFTB.bftservice.tools.ResponseCollector;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.grpc.Status.INVALID_ARGUMENT;

public class BFTServiceImpl extends BFTServiceGrpc.BFTServiceImplBase{
    private ReplicaManager replicas;

    //id of current read operation
    private int rid;

    //Next timestamp to be written
    private int wts;

    //current transaction_id
    private int next_transaction_id;

    // pending requests <public key, nonce>
    private HashMap<PublicKey, String> pendingRequests;

    // for PoW
    private int numZeroes;

    public BFTServiceImpl(String bankHost, int bankPort, int f) {
        this.replicas = new ReplicaManager(bankHost, bankPort, f);
        this.rid = 0;
        this.wts = 0;
        this.next_transaction_id = getNextTransactionId();
        this.pendingRequests = new HashMap<PublicKey, String>();
        this.numZeroes = 4;
    }

    @Override
    public void openAccount(openAccountRequest request, StreamObserver<openAccountResponse> responseObserver) {
        if (!CryptoHelper.isPoWValid(request.getMsg().toByteArray(), numZeroes, request.getPow())) {
            responseObserver.onError(INVALID_ARGUMENT.withDescription("openAccount: Invalid Proof of Work").asRuntimeException());
            return;
        }
        try {

            openAccountResponse response = null;
            for (Replica replica: replicas.getReplicas()) {
                ManagedChannel channel = ManagedChannelBuilder.forTarget(replica.getHost() + ":" + replica.getPort()).usePlaintext().build();
                BankServiceGrpc.BankServiceBlockingStub stub = BankServiceGrpc.newBlockingStub(channel);
                openAccountResponse receive_response = stub.openAccount(request);
                if(replica.isWriteReplica())
                    response = receive_response;
                channel.shutdown();
            }

            responseObserver.onNext(response);
            responseObserver.onCompleted();
		}
		catch (StatusRuntimeException e) {
			responseObserver.onError(e.getStatus().asRuntimeException());
		}
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void receiveAmount(receiveAmountRequest request, StreamObserver<receiveAmountResponse> responseObserver) {
        if (!CryptoHelper.isPoWValid(request.getMsg().toByteArray(), numZeroes, request.getPow())) {
            responseObserver.onError(INVALID_ARGUMENT.withDescription("receiveAmount: Invalid Proof of Work").asRuntimeException());
            return;
        }
        PublicKey key = CryptoHelper.publicKeyFromBase64(request.getMsg().getKey());
        String nonce = pendingRequests.get(key);
        if (nonce == null || !nonce.equals(request.getMsg().getNonce())) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("receiveAmount: Incorrect nonce").asRuntimeException());
            return;
        }

        List<ManagedChannel> channels = new ArrayList<ManagedChannel>();

        try {
            storeNonce(nonce);

            receiveAmountResponse response_from_writer = getRespReceiveFromWriter(request);

            //get the wts
            int wts_source = response_from_writer.getWtsSource();
            System.out.println(wts_source);
            int wts_dest = response_from_writer.getWtsDest();
            System.out.println(wts_dest);

            receiveAmountRequest newRequest = receiveAmountRequest.newBuilder().setMsg(request.getMsg())
                    .setSignature(request.getSignature()).setTimestampSource(wts_source).setTimestampDest(wts_dest).build();

            //broadcast for other replicas
            receiveAmountAsync(channels,newRequest);

            responseObserver.onNext(response_from_writer);
            responseObserver.onCompleted();

            receiveAmountResponse response = replicas.receiveAmount(request);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (StatusRuntimeException e) {
            responseObserver.onError(e.getStatus().asRuntimeException());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public receiveAmountResponse getRespReceiveFromWriter(receiveAmountRequest request) {

        Replica write_rep = replicas.getWriteRep();

        ManagedChannel channel = ManagedChannelBuilder.forTarget(write_rep.getHost() + ":" + write_rep.getPort()).usePlaintext().build();
        BankServiceGrpc.BankServiceBlockingStub stub_writer_replica = BankServiceGrpc.newBlockingStub(channel);

        return stub_writer_replica.receiveAmount(request);

    }

    public void receiveAmountAsync(List<ManagedChannel> channels, receiveAmountRequest request) throws InterruptedException {

        ResponseCollector<receiveAmountResponse> readBankCollector = new ResponseCollector<receiveAmountResponse>(this.replicas.getNumReplicas());

        for(Replica replica : this.replicas.getReplicas()){
            channels.add(this.asyncReceiveAmountRequest(replica, request, readBankCollector));
        }
        BFTLogger.LogDebug("Sent check Bank Account to all replicas");

        while (!readBankCollector.hasQuorum()) {
            Thread.sleep(5);
        }

        // Shutdown all the rec channels, even if not every request arrived
        shutdownChannels(channels);

    }

    public ManagedChannel asyncReceiveAmountRequest(Replica replica, receiveAmountRequest request,ResponseCollector<receiveAmountResponse> collector) {

        // Create a new channel for the given target
        ManagedChannel channel = ManagedChannelBuilder.forTarget(replica.getHost() + ":" + replica.getPort()).usePlaintext().build();

        // Create a stub for the above created channel
        BankServiceGrpc.BankServiceStub stub = BankServiceGrpc.newStub(channel);

        // Create a RecWriteRecordResponse callback
        StreamObserver<receiveAmountResponse> readStreamObserver = new RepStreamObserver<receiveAmountResponse>(
                collector);

        // BFTLogger.LogDebug("Sent Async check account request with rid " + rid);

        stub.receiveAmount(request, readStreamObserver);

        return channel;
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void sendAmount(sendAmountRequest request, StreamObserver<sendAmountResponse> responseObserver) {
        if (!CryptoHelper.isPoWValid(request.getMessage().toByteArray(), numZeroes, request.getPow())) {
            responseObserver.onError(INVALID_ARGUMENT.withDescription("sendAmount: Invalid Proof of Work").asRuntimeException());
            return;
        }
        PublicKey srcKey = CryptoHelper.publicKeyFromBase64(request.getMessage().getSource());
        String nonce = pendingRequests.get(srcKey);
        if (nonce == null || !nonce.equals(request.getMessage().getNonce())) {
            responseObserver.onError(
                    INVALID_ARGUMENT.withDescription("sendAmount: Incorrect nonce").asRuntimeException());
            return;
        }

        List<ManagedChannel> channels = new ArrayList<ManagedChannel>();

        try {
            storeNonce(nonce);

            //get response from writer
            System.out.println("Mandar a resposta para a replica writer");

            sendAmountResponse response_from_writer = getRespFromWriter(request,this.next_transaction_id);

            System.out.println("resposta da replica writer" + response_from_writer.toString());

            //get the wts
            int wts_source = response_from_writer.getWtsSource();
            System.out.println(wts_source);
            int wts_dest = response_from_writer.getWtsDest();
            System.out.println(wts_dest);

            sendAmountRequest newRequest = sendAmountRequest.newBuilder().setMessage(request.getMessage()).setSignature(request.getSignature()).setTimestampSource(wts_source).setTimestampDest(wts_dest).setNextTransactionId(this.next_transaction_id).build();

            System.out.println("request a mandar para as outras replicas " + newRequest.toString());

            //broadcast for other replicas
            sendAmountAsync(channels,newRequest);
            this.next_transaction_id += 1;
            responseObserver.onNext(response_from_writer);
            responseObserver.onCompleted();
		}
		catch (StatusRuntimeException e) {
			responseObserver.onError(e.getStatus().asRuntimeException());
		} catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public sendAmountResponse getRespFromWriter(sendAmountRequest request ,int next_transaction_id) {

        sendAmountRequest new_request = sendAmountRequest.newBuilder().setMessage(request.getMessage()).setSignature(request.getSignature()).setNextTransactionId(this.next_transaction_id).setTimestampSource(request.getTimestampSource()).setTimestampDest(request.getTimestampDest()).build();
        Replica write_rep = replicas.getWriteRep();

        ManagedChannel channel = ManagedChannelBuilder.forTarget(write_rep.getHost() + ":" + write_rep.getPort()).usePlaintext().build();
        BankServiceGrpc.BankServiceBlockingStub stub_writer_replica = BankServiceGrpc.newBlockingStub(channel);

        return stub_writer_replica.sendAmount(new_request);

    }

    public void sendAmountAsync(List<ManagedChannel> channels,sendAmountRequest request) throws InterruptedException {

        ResponseCollector<sendAmountResponse> readBankCollector = new ResponseCollector<sendAmountResponse>(this.replicas.getNumReplicas());

        Context ctx = Context.current().fork();
        ctx.run(() -> {
            for(Replica replica : this.replicas.getReplicas()){
                channels.add(this.asyncSendAmountRequest(replica, request, readBankCollector));
            }
        });
        BFTLogger.LogDebug("Sent check Bank Account to all replicas");

        while (!readBankCollector.hasQuorum()) {
            Thread.sleep(5);
        }

        // Shutdown all the rec channels, even if not every request arrived
        shutdownChannels(channels);

        System.out.println("Passei o quorom");


    }

    public ManagedChannel asyncSendAmountRequest(Replica replica,sendAmountRequest request,ResponseCollector<sendAmountResponse> collector) {

        ManagedChannel channel = null;
        // Create a new channel for the given target
        try {
             channel = ManagedChannelBuilder.forTarget(replica.getHost() + ":" + replica.getPort()).usePlaintext().build();

            // Create a stub for the above created channel
            BankServiceGrpc.BankServiceStub stub = BankServiceGrpc.newStub(channel);

            // Create a RecWriteRecordResponse callback
            StreamObserver<sendAmountResponse> readStreamObserver = new RepStreamObserver<sendAmountResponse>(
                    collector);

            // BFTLogger.LogDebug("Sent Async check account request with rid " + rid);

            stub.sendAmount(request, readStreamObserver);

            return channel;
        } catch (Exception e) {
            return channel.shutdown();
        }


    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void checkAccount(checkAccountRequest request, StreamObserver<checkAccountResponse> responseObserver) {

        List<ManagedChannel> channels = new ArrayList<ManagedChannel>();

        try {

            checkAccountResponse mostRecentValue = sendAsyncReadBankRequest(request,channels);
            BFTLogger.LogDebug("Sent all Read Record Requests");


            // WRITE_BACK
            //get the wts
            int highest_rid = mostRecentValue.getRid();

            checkAccountWriteRequest request1 = checkAccountWriteRequest.newBuilder().setMsg(mostRecentValue.getMsgResponse())
                    .setSignature("BFT SIGNATURE").setWts(highest_rid).setKey(request.getKey()).build();


            //broadcast for other replicas (ATOMIC REGISTER)
            checkAccountAsync(channels, request1);

            checkAccountResponse response = checkAccountResponse.newBuilder().setMsgResponse(mostRecentValue.getMsgResponse()).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();

        }
		catch (StatusRuntimeException e) {
			responseObserver.onError(e.getStatus().asRuntimeException());
		} catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void checkAccountAsync(List<ManagedChannel> channels,checkAccountWriteRequest request) throws InterruptedException {

        ResponseCollector<checkAccountWriteResponse> readBankCollector = new ResponseCollector<checkAccountWriteResponse>(this.replicas.getNumReplicas());

        Context ctx = Context.current().fork();
        ctx.run(() -> {
            for(Replica replica : this.replicas.getReplicas()){
                channels.add(this.asyncCheckAccountRequest(replica, request, readBankCollector));
            }
        });
        BFTLogger.LogDebug("Sent write check Bank Account to all replicas");


        //NÃO HÁ QUORUM NA ESCRITA MAS TBM N PODEMOS FECHAR OS CANAIS PORTANTO VER O QUE FAZER

            Thread.sleep(10);


        // Shutdown all the rec channels, even if not every request arrived
        shutdownChannels(channels);

    }

    public ManagedChannel asyncCheckAccountRequest(Replica replica,checkAccountWriteRequest request,ResponseCollector<checkAccountWriteResponse> collector) {

        // Create a new channel for the given target
        ManagedChannel channel = ManagedChannelBuilder.forTarget(replica.getHost() + ":" + replica.getPort()).usePlaintext().build();

        // Create a stub for the above created channel
        BankServiceGrpc.BankServiceStub stub = BankServiceGrpc.newStub(channel);

        // Create a RecWriteRecordResponse callback
        StreamObserver<checkAccountWriteResponse> readStreamObserver = new RepStreamObserver<checkAccountWriteResponse>(
                collector);

        // BFTLogger.LogDebug("Sent Async check account request with rid " + rid);

        stub.checkAccountWrite(request, readStreamObserver);

        return channel;
    }

    //////////////////////////////////////////////////     Acima estão as funções relacionadas com o write back e a principal
    //////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //////////////////////////////////////////////////      Em baixo estão as funções relacionadas com o read

    // Method to send a read record request to all recs
    private checkAccountResponse sendAsyncReadBankRequest(checkAccountRequest request, List<ManagedChannel> channels) throws InterruptedException {
          ResponseCollector<checkAccountResponse> readBankCollector = new ResponseCollector<checkAccountResponse>(this.replicas.getNumReplicas());

        Context ctx = Context.current().fork();
        ctx.run(() -> {
            for(Replica replica : this.replicas.getReplicas()){
                channels.add(this.asyncBankReadRequest(replica, request, readBankCollector));
            }
        });
          BFTLogger.LogDebug("Sent check Bank Account to all replicas");

          while (!readBankCollector.hasQuorum()) {
              Thread.sleep(5);
          }

          BFTLogger.LogDebug("Received Quorum number of responses");

          // Shutdown all the rec channels, even if not every request arrived
          shutdownChannels(channels);

          checkAccountResponse mostRecentValue = getMostRecentValueCheckAccount(readBankCollector);

          return mostRecentValue;
    }

    private checkAccountResponse getMostRecentValueCheckAccount(ResponseCollector<checkAccountResponse> readRecordCollector) {
        checkAccountResponse maxValue = null;
        int mostRecentTs = -1;
        // appends all responses that did not threw an error
        for (checkAccountResponse response : readRecordCollector.getResponses()) {
            int responseTs = response.getRid();
            if (mostRecentTs < responseTs) {
                maxValue = response;
                mostRecentTs = responseTs;
            }
        }
        BFTLogger.LogDebug("Obtained most recent value with TimeStamp " + mostRecentTs + " and value " + maxValue);
        return maxValue;
    }

    private ManagedChannel asyncBankReadRequest(Replica replica, checkAccountRequest request, ResponseCollector<checkAccountResponse> collector){

        // Create a new channel for the given target
        ManagedChannel channel = ManagedChannelBuilder.forTarget(replica.getHost() + ":" + replica.getPort()).usePlaintext().build();

        // Create a stub for the above created channel
        BankServiceGrpc.BankServiceStub stub = BankServiceGrpc.newStub(channel);

        // Create a RecWriteRecordResponse callback
        StreamObserver<checkAccountResponse> readStreamObserver = new RepStreamObserver<checkAccountResponse>(
                collector);

        BFTLogger.LogDebug("Sent Async check account request with rid " + rid);

        stub.checkAccount(request, readStreamObserver);

        return channel;
    }


    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void audit(auditRequest request, StreamObserver<auditResponse> responseObserver) {

        List<ManagedChannel> channels = new ArrayList<ManagedChannel>();

        try {

            auditResponse mostRecentValue = sendAuditAsyncReadBankRequest(request,channels);
			// auditResponse response = replicas.audit(request);



            int highest_rid = mostRecentValue.getRid();

            auditWriteRequest request1 = auditWriteRequest.newBuilder().setMsg(mostRecentValue.getMsg())
                    .setSignature("BFT SIGNATURE").setWts(highest_rid).setKey(request.getKey()).build();


            //broadcast for other replicas (ATOMIC REGISTER)
            auditAsync(channels, request1);

            auditResponse response = auditResponse.newBuilder().setMsg(mostRecentValue.getMsg()).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
		}
		catch (StatusRuntimeException e) {
			responseObserver.onError(e.getStatus().asRuntimeException());
		} catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private auditResponse getMostRecentValueAudit(ResponseCollector<auditResponse> readRecordCollector) {
        auditResponse maxValue = null;
        int mostRecentTs = -1;
        // appends all responses that did not threw an error
        for (auditResponse response : readRecordCollector.getResponses()) {
            int responseTs = response.getRid();
            if (mostRecentTs < responseTs) {
                maxValue = response;
                mostRecentTs = responseTs;
            }
        }
        BFTLogger.LogDebug("Obtained most recent value with TimeStamp " + mostRecentTs + " and value " + maxValue);
        return maxValue;
    }

    public void auditAsync(List<ManagedChannel> channels,auditWriteRequest request) throws InterruptedException {

        ResponseCollector<auditWriteResponse> readBankCollector = new ResponseCollector<auditWriteResponse>(this.replicas.getNumReplicas());

        Context ctx = Context.current().fork();
        ctx.run(() -> {
            for(Replica replica : this.replicas.getReplicas()){
                channels.add(this.asyncAuditRequest(replica, request, readBankCollector));
            }
        });
        BFTLogger.LogDebug("Sent write audit to all replicas");


        //NÃO HÁ QUORUM NA ESCRITA MAS TBM N PODEMOS FECHAR OS CANAIS PORTANTO VER O QUE FAZER

            Thread.sleep(10);


        // Shutdown all the rec channels, even if not every request arrived
        shutdownChannels(channels);

    }

    public ManagedChannel asyncAuditRequest(Replica replica,auditWriteRequest request,ResponseCollector<auditWriteResponse> collector) {

        // Create a new channel for the given target
        ManagedChannel channel = ManagedChannelBuilder.forTarget(replica.getHost() + ":" + replica.getPort()).usePlaintext().build();

        // Create a stub for the above created channel
        BankServiceGrpc.BankServiceStub stub = BankServiceGrpc.newStub(channel);

        // Create a RecWriteRecordResponse callback
        StreamObserver<auditWriteResponse> readStreamObserver = new RepStreamObserver<auditWriteResponse>(
                collector);

        // BFTLogger.LogDebug("Sent Async check account request with rid " + rid);

        stub.auditWrite(request, readStreamObserver);

        return channel;
    }

    //////////////////////////////////////////////////     Acima estão as funções relacionadas com o write back e a principal
    //////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //////////////////////////////////////////////////      Em baixo estão as funções relacionadas com o read

    private auditResponse sendAuditAsyncReadBankRequest(auditRequest request, List<ManagedChannel> channels) throws InterruptedException {
        ResponseCollector<auditResponse> readBankCollector = new ResponseCollector<auditResponse>(this.replicas.getNumReplicas());

        Context ctx = Context.current().fork();
        ctx.run(() -> {
            for(Replica replica : this.replicas.getReplicas()){
                channels.add(this.asyncAuditReadRequest(replica, request, readBankCollector));
            }
        });
        BFTLogger.LogDebug("Sent check Bank Account to all replicas");

        while (!readBankCollector.hasQuorum()) {
            Thread.sleep(5);
        }

        BFTLogger.LogDebug("Received Quorum number of responses");

        // Shutdown all the rec channels, even if not every request arrived
        shutdownChannels(channels);

        auditResponse mostRecentValue = getMostRecentValueAudit(readBankCollector);

        return mostRecentValue;
    }



    private ManagedChannel asyncAuditReadRequest(Replica replica, auditRequest request, ResponseCollector<auditResponse> collector){

        checkAccountRequest tagRequest = checkAccountRequest.newBuilder().setKey(request.getKey()).setRid(rid).build();

        // Create a new channel for the given target
        ManagedChannel channel = ManagedChannelBuilder.forTarget(replica.getHost() + ":" + replica.getPort()).usePlaintext().build();

        // Create a stub for the above created channel
        BankServiceGrpc.BankServiceStub stub = BankServiceGrpc.newStub(channel);

        // Create a RecWriteRecordResponse callback
        StreamObserver<auditResponse> readStreamObserver = new RepStreamObserver<auditResponse>(
                collector);

        BFTLogger.LogDebug("Sent Async check account request with rid " + rid);

        stub.audit(request, readStreamObserver);

        return channel;
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void getNonce(nonceRequest request, StreamObserver<nonceResponse> responseObserver) {
        PublicKey key = CryptoHelper.publicKeyFromBase64(request.getKey());
        String nonce = CryptoHelper.generateNonce();

        pendingRequests.put(key, nonce);

        nonceResponse response = nonceResponse.newBuilder().setNonce(nonce).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private void storeNonce(String nonce) throws InterruptedException {
        List<ManagedChannel> channels = new ArrayList<ManagedChannel>();

        // Send nonce for writer to store
        nonceWriteRequest new_request = nonceWriteRequest.newBuilder().setNonce(nonce).build();
        
        Replica write_rep = replicas.getWriteRep();
        ManagedChannel channel = ManagedChannelBuilder.forTarget(write_rep.getHost() + ":" + write_rep.getPort()).usePlaintext().build();

        BankServiceGrpc.BankServiceBlockingStub stub_writer_replica = BankServiceGrpc.newBlockingStub(channel);
        nonceWriteResponse writerResp = stub_writer_replica.nonceWrite(new_request);

        // Broadcast to replicas
        // String signature = CryptoHelper.signMessage(privatekey, writerResp.getNonceRegister());

        String signature = "";  // FIXME
        nonceWriteBackRequest replicaReq = nonceWriteBackRequest.newBuilder().setNonceRegister(writerResp.getNonceRegister())
                                                                    .setSignature(signature).build();
        storeNonceAsync(channels, replicaReq);
    }

    public void storeNonceAsync(List<ManagedChannel> channels, nonceWriteBackRequest request) throws InterruptedException {

        ResponseCollector<nonceWriteBackResponse> writeCollector = new ResponseCollector<nonceWriteBackResponse>(this.replicas.getNumReplicas());
        for(Replica replica : this.replicas.getReplicas()){
            if(!replica.isWriteReplica())
                channels.add(this.asyncNonceRequest(replica, request, writeCollector));
        }
        BFTLogger.LogDebug("Sent store nonce to all replicas");
        while (!writeCollector.hasQuorum()) {
            Thread.sleep(5);
        }
        // Shutdown all the rec channels, even if not every request arrived
        shutdownChannels(channels);

        System.out.println("Passei o quorom");
    }

    public ManagedChannel asyncNonceRequest(Replica replica, nonceWriteBackRequest request,ResponseCollector<nonceWriteBackResponse> collector) {
        ManagedChannel channel = null;
        // Create a new channel for the given target
        try {
             channel = ManagedChannelBuilder.forTarget(replica.getHost() + ":" + replica.getPort()).usePlaintext().build();
            // Create a stub for the above created channel
            BankServiceGrpc.BankServiceStub stub = BankServiceGrpc.newStub(channel);
            // Create a response callback
            StreamObserver<nonceWriteBackResponse> readStreamObserver = new RepStreamObserver<nonceWriteBackResponse>(
                    collector);
            // BFTLogger.LogDebug("Sent Async check account request with rid " + rid);
            stub.nonceWriteBack(request, readStreamObserver);
            return channel;
        } catch (Exception e) {
            return channel.shutdown();
        }
    }
    

    @Override
    public void checkStatus(checkStatusRequest request, StreamObserver<checkStatusResponse> responseObserver) {

        try {
			checkStatusResponse response = replicas.checkStatus(request);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
		}

		catch (StatusRuntimeException e) {
			responseObserver.onError(e.getStatus().asRuntimeException());
		}

    }

    private List<ManagedChannel> getChannels() {

        List<ManagedChannel> ret_list = new ArrayList<>();

        for(Replica replica : this.replicas.getReplicas()) {
            ManagedChannel channel = ManagedChannelBuilder.forTarget(replica.getHost() + ":" + replica.getPort()).usePlaintext().build();
            ret_list.add(channel);
        }

        return  ret_list;
    }

    // Method to shut down all channels in a list of channels
    private void shutdownChannels(List<ManagedChannel> channels) {
        channels.forEach(channel -> {
            channel.shutdown();
            System.out.println("Fechei canal");
        });
        channels.clear();
        BFTLogger.LogDebug("Shutdown all Replica channels");
    }

    private void printError(String function, String message) {
        System.out.println(function + ": " + message);
    }

    private int getNextTransactionId() {
        List<ManagedChannel> channels = new ArrayList<ManagedChannel>();
        maxTransactionIdRequest request = maxTransactionIdRequest.newBuilder().build();
        ResponseCollector<maxTransactionIdResponse> readBankCollector = new ResponseCollector<maxTransactionIdResponse>(this.replicas.getNumReplicas());
        for(Replica replica : this.replicas.getReplicas()){
            channels.add(this.asyncTransactionIdRequest(replica, request, readBankCollector));
        }
        BFTLogger.LogDebug("Sent transaction id request to all replicas");
        while (!readBankCollector.hasQuorum()) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        BFTLogger.LogDebug("Received Quorum number of responses");
        shutdownChannels(channels);
        int maxId = 0;
        for (maxTransactionIdResponse response : readBankCollector.getResponses()) {
            if (response.getId() > maxId) {
                maxId = response.getId();
            }
        }
        return maxId + 1;
    }

    private ManagedChannel asyncTransactionIdRequest(Replica replica, maxTransactionIdRequest request, ResponseCollector<maxTransactionIdResponse> collector) {
        // Create a new channel for the given target
        ManagedChannel channel = ManagedChannelBuilder.forTarget(replica.getHost() + ":" + replica.getPort()).usePlaintext().build();
        // Create a stub for the above created channel
        BankServiceGrpc.BankServiceStub stub = BankServiceGrpc.newStub(channel);
        // Create a callback
        StreamObserver<maxTransactionIdResponse> readStreamObserver = new RepStreamObserver<maxTransactionIdResponse>(
                collector);
        BFTLogger.LogDebug("Sent Async max transaction id request");
        stub.maxTransactionId(request, readStreamObserver);
        return channel;
    }

}
