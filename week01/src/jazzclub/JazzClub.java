package jazzclub;

import jazzclub.domain.*;
import jazzclub.service.JazzClubService;
import jazzclub.view.JazzClubView;

import java.util.Scanner;

public class JazzClub {
    private final Guest guest;
    private final Seat seat;
    private final Cashier cashier;
    private final Drink[] menu;
    private final Scanner sc;
    private final JazzClubService service;
    private final JazzClubView view;

    public JazzClub(Guest guest, Seat seat, Cashier cashier, Drink[] menu, Scanner sc) {
        this.guest = guest;
        this.seat = seat;
        this.cashier = cashier;
        this.menu = menu;
        this.sc = sc;
        this.service = new JazzClubService(guest, seat, cashier, menu);
        this.view = new JazzClubView(sc);
    }

    private void handleSelectSeat() {
        this.view.printMessage("\n=====[좌석 선택]=====\n");

        try {
            this.service.selectSeat();
        } catch (IllegalStateException e) {
            this.view.printMessage(e.getMessage());
        }
    }

    private void handleChangeSeat() {
        this.view.printMessage("\n=====[좌석 이동]=====\n");

        try {
            this.service.changeSeat();
        } catch (IllegalStateException e) {
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

            int menuNumber = this.view.getMenuInput();

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

    private void handleExit() {
        this.view.printMessage("찾아주셔서 감사합니다. 또 오세요.");

        int sales = this.cashier.getSales();
        if (sales > 0) {
            this.view.printMessage("총 사용하신 금액: " + sales);
        }
    }

    public void run() {
        this.view.printMessage("안녕하세요, Bluer Jazz Club입니다.");

        boolean isRunning = true;

        while (isRunning) {
            this.view.printMainMenu();

            int input = sc.nextInt();

            switch (input) {
                case 1 -> handleSelectSeat();
                case 2 -> handleChangeSeat();
                case 3 -> handleOrder();
                case 0 -> {
                    handleExit();
                    isRunning = false;
                }
                default -> this.view.printMessage("잘못된 번호를 입력하셨습니다. 다시 입력해 주세요. (입력하신 번호: " + input + ")\n");
            }
        }
    }

    public enum SeatMode {
        SELECT, CHANGE
    }

}
