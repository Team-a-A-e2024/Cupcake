package app.entities;

import java.util.Objects;

public class Topping {
    private String topping;
    private double price;

    public Topping(String topping, double price) {
        this.topping = topping;
        this.price = price;
    }

    public String getTopping() {
        return topping;
    }

    public void setTopping(String topping) {
        this.topping = topping;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Toppings{" +
                "topping='" + topping + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Topping topping1)) return false;
        return Double.compare(price, topping1.price) == 0 && Objects.equals(topping, topping1.topping);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topping, price);
    }
}
