package jazzclub;

public class Main {
    public static void main(String[] args) {
        Guest guest = new Guest(100);
        Seat seat = new Seat(10);

        System.out.println("Hello JazzClub");
        System.out.println("Guest's initial Information");
        System.out.println(guest.getCash());
        System.out.println(guest.getCurrentSeat());

        int money = 50;

        if (guest.canSpendCash(money)) {
            guest.spendCash(money);
            guest.setCurrentSeat(1);
        }

        int currentSeat = 0;

        System.out.println("Guest's Information after test");
        System.out.println(guest.getCash());
        System.out.println(guest.getCurrentSeat());

        System.out.println("Seat Simulation");
        System.out.println("현재 좌석 현황");
        seat.showSeatsExcluding(currentSeat);

        System.out.println("좌석 점유: 1");
        currentSeat = 0;
        seat.occupySeat(currentSeat);
        seat.showSeatsExcluding(currentSeat);

        System.out.println("좌석 점유: 1 다시 시도");
        seat.occupySeat(currentSeat);

        System.out.println("좌석 변경: 1->2");
        seat.releaseSeat(currentSeat);
        currentSeat = 2;
        seat.occupySeat(currentSeat);

        System.out.println("좌석 현황");
        seat.showSeatsExcluding(currentSeat);
    }
}
