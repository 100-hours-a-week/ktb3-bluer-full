package jazzclub;

import jazzclub.domain.*;
import jazzclub.util.Constants;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Guest guest = new Guest(Constants.Guest.CASH);
        Seat seat = new Seat(Constants.Seat.TOTAL_SEATS);
        Cashier cashier = new Cashier();
        Drink[] menu = {
                new Cocktail("Tequila", 30, 0.4f),
                new Cocktail("Mojito", 25, 0.3f),
                new Coffee("Americano", 20, "Java")
        };

        JazzClub jazzclub = new JazzClub(guest, seat, cashier, menu, sc);

        jazzclub.run();
    }
}
