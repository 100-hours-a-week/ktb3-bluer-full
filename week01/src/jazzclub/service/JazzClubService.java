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

    // Seat
//    private void allocateSeat(JazzClub.SeatMode mode, int excludedSeat, Guest guest) {
//        boolean seatSelected = false;
//
//        while (!seatSelected) {
//            // JazzClubView.printMessage("원하는 좌석의 번호를 입력해 주세요.\n");
//            // JazzClubView.printMessage("뒤로 돌아가기 원하신다면 0번을 입력해 주세요.\n");
//            this.showSeatsExcluding(excludedSeat);
//
//            int selectedSeatNumber = sc.nextInt();
//
//            if (selectedSeatNumber == 0) {
//                return;
//            }
//
//            if (mode.equals(JazzClub.SeatMode.CHANGE) && selectedSeatNumber == excludedSeat) {
//                // JazzClubView.printMessage("현재 좌석과 동일한 좌석입니다.");
//                continue;
//            }
//            if (!this.isValidSeatNumber(selectedSeatNumber)) {
//                this.printIsWrongSeatNumber(selectedSeatNumber);
//                continue;
//            }
//            if (!this.isSeatAvailable(selectedSeatNumber)) {
//                // JazzClubView.printMessage("이미 선점된 좌석입니다.");
//                continue;
//            }
//
//            this.occupySeat(selectedSeatNumber);
//            guest.setCurrentSeat(selectedSeatNumber);
//
//            // JazzClubView.printMessage("좌석 선택이 완료되었습니다. 발급받은 입장권을 갖고 들어가 주세요.\n\n");
//            // JazzClubView.printTicket(selectedSeatNumber);
//
//            seatSelected = true;
//        }
//    }

//    private void seatLoop(JazzClub.SeatMode mode, int excludedSeat) {
//
//    }



    public void selectSeat() {

//        if (this.guest.hasSeat()) {
//            throw new IllegalStateException("이미 보유한 좌석이 있습니다. 좌석 이동 메뉴를 이용해 주세요.");
//        }
        int NONE_SELECT = -1;
//        this.allocateSeat(JazzClub.SeatMode.SELECT, NONE_SELECT, this.guest);
    }

    public void changeSeat() {
        if (!this.guest.hasSeat()) {
            throw new IllegalStateException("보유한 좌석이 없습니다. 좌석 선택 메뉴를 이용해 주세요.");
        }
        int currentSeat = this.guest.getCurrentSeat();

        this.seat.allocateSeat(JazzClub.SeatMode.CHANGE, currentSeat, this.guest);
        this.seat.releaseSeat(currentSeat);
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
