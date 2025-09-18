package jazzclub;

import jazzclub.domain.*;
import jazzclub.service.JazzClubService;
import jazzclub.util.Constants;
import jazzclub.view.JazzClubView;

import java.util.Scanner;
import java.util.concurrent.*;

public class JazzClub {
    private final Guest guest;
    private final Seat seat;
    private final Cashier cashier;
    private final Drink[] menu;
    private final Scanner sc;
    private final JazzClubService service;
    private final JazzClubView view;

    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> timerFuture;

    private int remainingSeconds = Constants.Seat.INITIAL_REMAINING_SECONDS;

    public JazzClub(Guest guest, Seat seat, Cashier cashier, Drink[] menu, Scanner sc) {
        this.guest = guest;
        this.seat = seat;
        this.cashier = cashier;
        this.menu = menu;
        this.sc = sc;
        this.service = new JazzClubService(guest, seat, cashier, menu);
        this.view = new JazzClubView(sc);
    }

    private void startGuestTimer() {
        stopGuestTimer();
        timerFuture = executor.scheduleAtFixedRate(() -> {
            if (remainingSeconds < 1) {
                view.printMessage("고객님의 이용 시간이 모두 끝났습니다. 찾아주셔서 감사합니다.");
                handleExit();
            }
            remainingSeconds--;
        }, 1, 1, TimeUnit.SECONDS);
    }

    private void stopGuestTimer() {
        if (timerFuture != null && !timerFuture.isCancelled()) {
            timerFuture.cancel(true);
        }
    }

    private void allocateSeatLoop(JazzClub.SeatMode mode, int excludedSeat) {
        boolean seatSelected = false;
        
        while (!seatSelected) {
            this.view.printMessage("원하는 좌석의 번호를 입력해 주세요.\n");
            this.view.printMessage("뒤로 돌아가기 원하신다면 0번을 입력해 주세요.\n");
            this.view.showSeatsExcluding(this.seat, excludedSeat);

            int selectedSeatNumber = this.view.getInputNumber();

            if (selectedSeatNumber == 0) {
                return;
            }

            if (mode == SeatMode.CHANGE && selectedSeatNumber == excludedSeat) {
                this.view.printMessage("현재 좌석과 동일한 좌석입니다.");
                continue;
            }

            if (!this.seat.isValidSeatNumber(selectedSeatNumber)) {
                this.view.printIsWrongSeatNumber(selectedSeatNumber, this.seat.getTotalSeats());
                continue;
            }
            if (!this.seat.isSeatAvailable(selectedSeatNumber)) {
                this.view.printMessage("이미 선점된 좌석입니다.");
                continue;
            }

            this.service.occupySeat(selectedSeatNumber);
            this.view.printMessage("좌석 선택이 완료되었습니다. 발급받은 입장권을 갖고 들어가 주세요.\n\n");
            this.view.printTicket(selectedSeatNumber);

            seatSelected = true;
        }
    }


    private void handleSelectSeat() {
        this.view.printMessage("\n=====[좌석 선택]=====\n");

        if (this.guest.hasSeat()) {
            this.view.printMessage("이미 보유한 좌석이 있습니다. 좌석 이동 메뉴를 이용해 주세요.");
            return;
        }
        try {
            this.allocateSeatLoop(SeatMode.SELECT, Constants.Seat.NO_SEAT);
            if (this.guest.hasSeat()) {
                startGuestTimer();
            }
        } catch (Exception e) {
            this.view.printMessage(e.getMessage());
        }
    }

    private void handleChangeSeat() {
        this.view.printMessage("\n=====[좌석 이동]=====\n");

        if (!this.guest.hasSeat()) {
            this.view.printMessage("보유한 좌석이 없습니다. 좌석 선택 메뉴를 이용해 주세요.");
            return;
        }

        int currentSeat = this.guest.getCurrentSeat();

        try {
            this.service.releaseSeat(currentSeat);
            this.allocateSeatLoop(SeatMode.CHANGE, currentSeat);
        } catch (Exception e) {
            this.view.printMessage(e.getMessage());
        }
    }

    private void handleOrder() {
        this.view.printMessage("\n=====[음료 주문]=====\n");

        if (!this.service.isGuestCanOrder()) {
            this.view.printMessage("좌석이 있는 고객만 음료 주문이 가능합니다. 좌석을 먼저 선택해 주세요.");
            return;
        }


        boolean orderCompleted = false;

        while (!orderCompleted) {
            this.view.printMessage("""
                                        구매하기 원하는 음료의 번호를 입력해 주세요.
                                        - 뒤로 돌아가기 원하신다면 0번을 입력해 주세요.
                    """);
            this.view.printDrinksMenu(menu);

            int menuNumber = this.view.getInputNumber();

            if (menuNumber == 0) {
                return;
            }

            try {
                Drink orderedDrink = service.orderDrink(menuNumber);
                this.view.printMessage("\n🎉 음료 구매 완료! 🎉");
                this.view.printMessage("선택한 음료: " + orderedDrink.getName() + " (" + orderedDrink.getDetail() + ")\n");

                if (orderedDrink instanceof Coffee) {
                    this.view.printCoffeeASCII();
                } else if (orderedDrink instanceof Cocktail) {
                    this.view.printCocktailASCII();
                }

                orderCompleted = true;
            } catch (Exception e) {
                this.view.printMessage(e.getMessage());
            }
        }
    }

    private void handleExtendTime() {
        if (this.guest.canSpendCash(Constants.Seat.EXTEND_COST)) {
            this.guest.spendCash(Constants.Seat.EXTEND_COST);
            remainingSeconds += Constants.Seat.EXTEND_SECONDS;
            this.view.printMessage("시간 연장이 완료되었습니다. 남은 시간: " + remainingSeconds, true);
        } else {
            this.view.printMessage("잔액이 부족하여 시간 연장이 불가능합니다.\n");
        }
    }

    private void handleExit() {
        handleExit(0); 
    }

    private void handleExit(int statusCode) {
        stopGuestTimer();
        this.view.printMessage("찾아주셔서 감사합니다. 또 오세요.");

        int sales = this.cashier.getSales();
        if (sales > 0) {
            this.view.printMessage("총 사용하신 금액: " + sales);
        }
        System.exit(statusCode);
    }

    public void run() {
        this.view.printMessage("안녕하세요, Bluer Jazz Club입니다.");

        boolean isRunning = true;

        while (isRunning) {
            this.view.printMainMenu();


            int input = view.getInputNumber();

            switch (input) {
                case 1 -> handleSelectSeat();
                case 2 -> handleChangeSeat();
                case 3 -> handleOrder();
                case 4 -> handleExtendTime();
                case 0 -> {
                    isRunning = false;
                    handleExit();
                }
                default -> this.view.printMessage("잘못된 번호를 입력하셨습니다. 다시 입력해 주세요. (입력하신 번호: " + input + ")\n");
            }
        }
    }

    public enum SeatMode {
        SELECT, CHANGE
    }

}
