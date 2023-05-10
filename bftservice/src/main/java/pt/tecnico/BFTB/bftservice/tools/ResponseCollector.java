package pt.tecnico.BFTB.bftservice.tools;

import java.util.ArrayList;
import java.util.List;

public class ResponseCollector<GeneratedMessageV3> {
    private int totalResponses = 0;
    private int quorum = 0;
    private List<GeneratedMessageV3> responses;

    public ResponseCollector(int n) {
        setTotalResponses(n);
        this.responses = new ArrayList<GeneratedMessageV3>();
    }

    public List<GeneratedMessageV3> getResponses() {
        return responses;
    }

    public synchronized void addResponse(GeneratedMessageV3 response) {
        this.responses.add(response);
    }

    public void setResponses(List<GeneratedMessageV3> responses) {
        this.responses = responses;
    }

    public void setTotalResponses(int n) {
        this.totalResponses = n;
        this.quorum = Math.round((n + 1) / 2);
    }

    public int getTotalResponses() {
        return totalResponses;
    }

    public synchronized boolean hasQuorum() {
        return this.responses.size() >= this.quorum;
    }
}
