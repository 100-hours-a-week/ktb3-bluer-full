package jazzclub.service;

import jazzclub.domain.*;


public class JazzClubService {
    private final Guest guest;
    private final Seat seat;
    private final Cashier cashier;
    private final Drink[] menu;

    public JazzClubService(Guest guest, Seat seat, Cashier cashier, Drink[] menu) { 
        this.guest = guest;
        this.seat = seat;
        this.cashier = cashier;
        this.menu = menu;
    }

    // Seat

    public void occupySeat(int seatNumber) {
        this.seat.setSeatsState(seatNumber, Seat.SeatState.OCCUPIED);
        this.guest.setCurrentSeat(seatNumber);
    }


    public void releaseSeat(int seatNumber) {
        this.seat.setSeatsState(seatNumber, Seat.SeatState.EMPTY);
        this.guest.setCurrentSeat(Guest.NO_SEAT);
    }

    // Order

    public boolean isGuestCanOrder() {
        return this.guest.hasSeat();
    }

    public Drink orderDrink(int menuNumber) {
        if (menuNumber < 1 || menuNumber > menu.length) {
            throw new IllegalArgumentException(
                String.format("\n=====\n잘못된 입력입니다. 유효한 숫자: %d ~ %d\n입력한 숫자: %d\n",
                    1, this.menu.length, menuNumber
            ));
        }

        Drink selectedMenu = this.menu[menuNumber - 1];
        int selectedMenuPrice = selectedMenu.getPrice();

        if (!this.guest.canSpendCash(selectedMenuPrice)) {
            throw new IllegalArgumentException(
                    "잔액이 부족합니다. 다른 음료를 선택해 주세요.\n\n\n\n"
            );
        }

        this.guest.spendCash(selectedMenuPrice);
        this.cashier.addSale(selectedMenuPrice);

        return selectedMenu;
    }
}
