public class Deposit {
    private String customer;
    private int id;
    private int initialBalance;
    private int upperBound;

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInitialBalance(int initialBalance) {
        this.initialBalance = initialBalance;
    }

    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }

    public String getCustomer() {
        return customer;
    }

    public int getId() {
        return id;
    }

    public int getInitialBalance() {
        return initialBalance;
    }

    public int getUpperBound() {
        return upperBound;
    }
}
