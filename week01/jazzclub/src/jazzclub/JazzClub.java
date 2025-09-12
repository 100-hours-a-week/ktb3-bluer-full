package jazzclub;

public class JazzClub {
    private Guest guest;
    private Seat seat;
    private Cashier cashier;
    private Drink[] menu;

    public JazzClub(Guest guest, Seat seat, Cashier cashier, Drink[] menu) {
        this.guest = guest;
        this.seat = seat;
        this.cashier = cashier;
        this.menu = menu;
    }

    public void run() {
        System.out.println("run");
    }
}
