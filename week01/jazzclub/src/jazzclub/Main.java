package jazzclub;

public class Main {
    public static void main(String[] args) {
        Guest guest = new Guest(100);

        System.out.println("Hello JazzClub");
        System.out.println("Guest's initial Information");
        System.out.println(guest.getCash());
        System.out.println(guest.getCurrentSeat());

        int money = 50;

        if (guest.canSpendCash(money)) {
            guest.spendCash(money);
            guest.setCurrentSeat(1);
        }

        System.out.println("Guest's Information after test");
        System.out.println(guest.getCash());
        System.out.println(guest.getCurrentSeat());
    }
}
