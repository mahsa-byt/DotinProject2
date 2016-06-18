import java.io.Serializable;
import java.math.BigDecimal;

public class Transaction implements Serializable {
    private int transactionId;
    private String transactionType;
    private BigDecimal transactionAmount;
    private int transactionDeposit;
    private boolean requestIsValid = true;
    private int terminalId;

    public Transaction() {

    }

    public Transaction(int id, String type, BigDecimal amount, int deposit) {
        this.transactionId = id;
        this.transactionAmount = amount;
        this.transactionType = type;
        this.transactionDeposit = deposit;
    }

    public void setId(int id) {
        this.transactionId = id;
    }

    public void setType(String type) {
        this.transactionType = type;
    }

    public void setAmount(BigDecimal amount) {
        this.transactionAmount = amount;
    }

    public void setDeposit(int deposit) {
        this.transactionDeposit = deposit;
    }

    public void setRequestIsValid(boolean requestIsValid) {
        this.requestIsValid = requestIsValid;
    }

    public int getId() {
        return transactionId;
    }

    public String getType() {
        return transactionType;
    }

    public BigDecimal getAmount() {
        return transactionAmount;
    }

    public int getDeposit() {
        return transactionDeposit;
    }

    public boolean getRequestIsValid() {
        return requestIsValid;
    }

    public int getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(int terminalId) {
        this.terminalId = terminalId;
    }

    public void validateRequest(Deposit deposit) {
        if (transactionType.equals("deposit")) {
            if ((deposit.getInitialBalance().add(transactionAmount)).compareTo(deposit.getUpperBound()) == 1  ||
                    /*deposit.getInitialBalance().add(transactionAmount).compareTo(deposit.getUpperBound()) == 0 ||*/
                    transactionAmount.compareTo(BigDecimal.ZERO)== -1) {
                requestIsValid = false;
            }
        } else if (transactionType.equals("withdraw")) {
            if ((deposit.getInitialBalance().add(transactionAmount)).compareTo(deposit.getUpperBound()) == 1 ||
                   /* deposit.getInitialBalance().add(transactionAmount).compareTo(deposit.getUpperBound()) == 0 ||*/
                    transactionAmount.compareTo(new BigDecimal(0)) == -1) {
                requestIsValid = false;
            }
        } else {
            requestIsValid = false;
            System.out.println("Invalid deposit type");
        }
    }

    public void applyChanges(Deposit deposit) {
        if (transactionType.equals("deposit")) {
            deposit.setInitialBalance(transactionAmount.add(deposit.getInitialBalance()));
        }
        else if (transactionType.equals("withdraw")){
            deposit.setInitialBalance(transactionAmount.subtract(deposit.getInitialBalance()));
        }
        else {
            System.out.println("Fail to apply");
        }
    }
    public String transactionToString(){
        String transactionToString = transactionId+","+ transactionType+","+transactionAmount+","+transactionDeposit+",";
        return transactionToString;
    }
}
