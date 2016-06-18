import java.math.BigDecimal;

public class Deposit {
    private String customer;
    private int id;
    private BigDecimal initialBalance;
    private BigDecimal upperBound;

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public void setUpperBound(BigDecimal upperBound) {
        this.upperBound = upperBound;
    }

    public String getCustomer() {
        return customer;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public BigDecimal getUpperBound() {
        return upperBound;
    }
}
