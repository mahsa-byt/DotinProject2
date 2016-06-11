import java.io.Serializable;

public class Transaction implements Serializable{
    private int id;
    private String type;
    private int amount;
    private int deposit;

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public int getDeposit() {
        return deposit;
    }

    public Transaction(){

    }
    public Transaction(int id, String type, int amount, int deposit){
        this.id=id;
        this.amount=amount;
        this.type=type;
        this.deposit=deposit;
    }
}
