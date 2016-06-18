import java.math.BigDecimal;

public class ResponseXMLFields {
    private int transactionId;
    private String transactionType;
    private int depositId;
    private BigDecimal depositAmount;
    private String message;
    private int typeOfResponse;

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setDepositId(int depositId) {
        this.depositId = depositId;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTypeOfResponse(int typeOfResponse) {
        this.typeOfResponse = typeOfResponse;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public int getDepositId() {
        return depositId;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public String getMessage() {
        return message;
    }

    public int getTypeOfResponse() {
        return typeOfResponse;
    }
}

