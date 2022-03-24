package pt.tecnico.BFTB.bank.pojos;

import java.util.Date;

public class Transaction {

    // flag: 0 for '-' and 1 for '+'
    int flag;
    int amount;
    // status: 0 for not confirmed and 1 for confirmed
    int status;
    // timeStamp guarantees freshness
    String timeStamp;

    public Transaction(int flag, int amount, int status, String timeStamp) {
        this.flag = flag;
        this.amount = amount;
        this.status = status;
        this.timeStamp = timeStamp;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
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
        if(flag == 1){
            depOrWith = "+";
        } else {
            depOrWith = "-";
        }
        return "Transaction{ " + depOrWith +
                amount +
                ", " + status + ", " + timeStamp +
                '}';
    }
}
