import java.util.ArrayList;

public class ServerInformation {
    private int port;
    private ArrayList<Deposit> deposits;
    private String outLog;

    public void setPort(int port) {
        this.port = port;
    }

    public void setDeposits(ArrayList<Deposit> deposits) {
        this.deposits = deposits;
    }

    public void setOutLog(String outLog) {
        this.outLog = outLog;
    }

    public int getPort() {
        return port;
    }

    public ArrayList<Deposit> getDeposits() {
        return deposits;
    }

    public String getOutLog() {
        return outLog;
    }
}
