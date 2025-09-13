package jazzclub;

public class Cashier {
    private int sales = 0;


    public int getSales() {
        return this.sales;
    }

    private void setSales(int value) {
        this.sales = value;
    }

    public void addSale(int amount) {
        this.setSales(this.getSales() + amount);
    }
}
