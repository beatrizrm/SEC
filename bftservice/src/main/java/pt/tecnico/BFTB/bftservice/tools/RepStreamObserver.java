package pt.tecnico.BFTB.bftservice.tools;

import io.grpc.stub.StreamObserver;

public class RepStreamObserver<V> implements StreamObserver<V> {

    private ResponseCollector<V> collector;

    public RepStreamObserver(ResponseCollector<V> collector) {
        this.collector = collector;
    }

    @Override
    public void onNext(V value) {
        BFTLogger.LogDebug("Received a new response: " + value);
        collector.addResponse(value);
    }

    @Override
    public void onError(Throwable t) {
        BFTLogger.LogError("Received an error: " + t.getMessage());
    }

    @Override
    public void onCompleted() {
        // Empty
    }

}