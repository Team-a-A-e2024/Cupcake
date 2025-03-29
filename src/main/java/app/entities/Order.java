package app.entities;

import java.util.Objects;

public class Order {
    private int id;
    private int userId;
    private String topping;
    private String bottom;
    private int amount;
    private boolean isProcessed;

    public Order(int id, int userId, String topping, String bottom, int amount, boolean isProcessed) {
        this.id = id;
        this.userId = userId;
        this.topping = topping;
        this.bottom = bottom;
        this.amount = amount;
        this.isProcessed = isProcessed;
    }

    public Order(int userId, String topping, String bottom, int amount, boolean isProcessed) {
        this.userId = userId;
        this.topping = topping;
        this.bottom = bottom;
        this.amount = amount;
        this.isProcessed = isProcessed;
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

    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean processed) {
        isProcessed = processed;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", topping='" + topping + '\'' +
                ", bottom='" + bottom + '\'' +
                ", amount=" + amount +
                ", isProcessed=" + isProcessed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return id == order.id
                && userId == order.userId
                && amount == order.amount
                && isProcessed == order.isProcessed
                && Objects.equals(topping, order.topping)
                && Objects.equals(bottom, order.bottom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, topping, bottom, amount, isProcessed);
    }
}
