package jazzclub.service;

import jazzclub.JazzClub;
import jazzclub.domain.*;
import jazzclub.view.JazzClubView;


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

    public void selectSeat() {
        if (this.guest.hasSeat()) {
            throw new IllegalStateException("이미 보유한 좌석이 있습니다. 좌석 이동 메뉴를 이용해 주세요.");
        }
        int NONE_SELECT = -1;
        this.seat.allocateSeat(JazzClub.SeatMode.SELECT, NONE_SELECT, this.guest);
    }

    public void changeSeat() {
        if (!this.guest.hasSeat()) {
            throw new IllegalStateException("보유한 좌석이 없습니다. 좌석 선택 메뉴를 이용해 주세요.");
        }
        int currentSeat = this.guest.getCurrentSeat();

        this.seat.allocateSeat(JazzClub.SeatMode.CHANGE, currentSeat, this.guest);
        this.seat.releaseSeat(currentSeat);
    }


}
