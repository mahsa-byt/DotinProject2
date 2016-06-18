import java.io.Serializable;
import java.util.ArrayList;

public class Terminal implements Serializable {
    private int terminalId;
    private String terminalType;
    private String serverIP;
    private int serverPort;
    private String outLogPath;
    ArrayList<Transaction> transactions;

    public void setId(int id) {
        this.terminalId = id;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public void setOutLogPath(String outLogPath) {
        this.outLogPath = outLogPath;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public int getId() {
        return terminalId;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public String getServerIP() {
        return serverIP;
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getOutLogPath() {
        return outLogPath;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public Terminal(){

    }
}
