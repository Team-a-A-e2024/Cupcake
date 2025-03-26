package app.entities;

import java.util.Objects;

public class Bottom {
    private String bottom;
    private double price;

    public Bottom(String bottom, double price) {
        this.bottom = bottom;
        this.price = price;
    }

    public String getBottom() {
        return bottom;
    }

    public void setBottom(String bottom) {
        this.bottom = bottom;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Bottoms{" +
                "bottoms='" + bottom + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bottom bottom1)) return false;
        return Double.compare(price, bottom1.price) == 0 && Objects.equals(bottom, bottom1.bottom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bottom, price);
    }
}
