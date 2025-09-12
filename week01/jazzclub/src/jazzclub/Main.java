package jazzclub;

public class Main {
    public static void main(String[] args) {
        Guest guest = new Guest(100);
        Seat seat = new Seat(10);
        Cashier cashier = new Cashier();
        Drink[] menu = {
                new Cocktail("Tequila", 30, 0.4f),
                new Cocktail("Mojito", 25, 0.3f),
                new Coffee("Americano", 20, "Java")
        };

        JazzClub jazzclub = new JazzClub(guest, seat, cashier, menu);

        jazzclub.run();
    }
}
