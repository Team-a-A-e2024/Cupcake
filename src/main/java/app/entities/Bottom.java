package app.entities;

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
}
