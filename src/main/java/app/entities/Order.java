package app.entities;

public class Order {

    private int id;
    private int userId;
    private String topping;
    private String bottom;
    private int amount;
    private boolean is_Processed;

    public Order(int id, int userId, String topping, String bottom, int amount, boolean is_Processed) {
        this.id = id;
        this.userId = userId;
        this.topping = topping;
        this.bottom = bottom;
        this.amount = amount;
        this.is_Processed = is_Processed;
    }

    public Order(String topping, String bottom, int amount, boolean is_Processed) {
        this.topping = topping;
        this.bottom = bottom;
        this.amount = amount;
        this.is_Processed = is_Processed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTopping() {
        return topping;
    }

    public void setTopping(String topping) {
        this.topping = topping;
    }

    public String getBottom() {
        return bottom;
    }

    public void setBottom(String bottom) {
        this.bottom = bottom;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isIs_Processed() {
        return is_Processed;
    }

    public void setIs_Processed(boolean is_Processed) {
        this.is_Processed = is_Processed;
    }
}
