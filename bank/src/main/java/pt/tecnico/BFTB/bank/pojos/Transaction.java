package pt.tecnico.BFTB.bank.pojos;

import java.security.PublicKey;
import java.util.Date;

public class Transaction {

    // id: transaction identifier
    private int id;
    // source: transaction source
    private PublicKey source;
    // destination: transaction destination
    private PublicKey destination;
    // sign: 0 for '-' and 1 for '+'
    private int sign;
    // amount: money involved in the transaction
    private int amount;
    // status: 0 for not confirmed and 1 for confirmed
    private int status;
    // timeStamp guarantees freshness
    private String timeStamp;

    public Transaction(int id, PublicKey source, PublicKey destination, int sign, int amount, int status, String timeStamp) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.sign = sign;
        this.amount = amount;
        this.status = status;
        this.timeStamp = timeStamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PublicKey getSource() {
        return source;
    }

    public void setSource(PublicKey source) {
        this.source = source;
    }

    public PublicKey getDestination() {
        return destination;
    }

    public void setDestination(PublicKey destination) {
        this.destination = destination;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        String depOrWith = "";
        if(sign == 1){
            depOrWith = "+";
        } else {
            depOrWith = "-";
        }
        return " Transaction{ "+ id + "/ " + source + ", "+ destination + "/ "+ depOrWith + amount +
                "/ " + status + "/ " + timeStamp +
                " }";
    }
}
