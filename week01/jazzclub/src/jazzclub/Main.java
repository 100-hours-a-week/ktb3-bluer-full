package jazzclub;

public class Main {
    public static void main(String[] args) {
        Guest guest = new Guest(100);
        Seat seat = new Seat(10);
        Cashier cashier = new Cashier();

        System.out.println("Cashier default sales: " + cashier.getSales());
        System.out.println("Guest default cash: " + guest.getCash());

        System.out.println("Customer buy Cocktail");

        int cocktail = 10;
        guest.spendCash(cocktail);
        cashier.addSale(cocktail);

        System.out.println("Cashier total sales: " + cashier.getSales());
        System.out.println("Guest current cash: " + guest.getCash());
    }
}
