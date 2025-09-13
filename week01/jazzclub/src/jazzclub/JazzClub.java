package jazzclub;

import java.util.Scanner;

public class JazzClub {
    private final int NONE_SELECT = -1;
    private final Guest guest;
    private final Seat seat;
    private final Cashier cashier;
    private final Drink[] menu;
    private final Scanner sc;

    public JazzClub(Guest guest, Seat seat, Cashier cashier, Drink[] menu, Scanner sc) {
        this.guest = guest;
        this.seat = seat;
        this.cashier = cashier;
        this.menu = menu;
        this.sc = sc;
    }


    public void allocateSeat(SeatMode mode, int excludedSeat) {
        while (true) {
            System.out.println("원하는 좌석의 번호를 입력해 주세요.\n");
            System.out.println("뒤로 돌아가기 원하신다면 0번을 입력해 주세요.\n");
            this.seat.showSeatsExcluding(excludedSeat);

            int selectedSeatNumber = sc.nextInt();

            if (selectedSeatNumber == 0) {
                break;
            }

            if (mode.equals(SeatMode.CHANGE) && selectedSeatNumber == excludedSeat) {
                System.out.println("현재 좌석과 동일한 좌석입니다.");
                continue;
            }
            if (!this.seat.isValidSeatNumber(selectedSeatNumber)) {
                this.seat.printIsWrongSeatNumber(selectedSeatNumber);
                continue;
            }
            if (!this.seat.isSeatAvailable(selectedSeatNumber)) {
                System.out.println("이미 선점된 좌석입니다.");
                continue;
            }

            this.seat.occupySeat(selectedSeatNumber);
            this.guest.setCurrentSeat(selectedSeatNumber);

            System.out.println("좌석 선택이 완료되었습니다. 발급받은 입장권을 갖고 들어가 주세요.\n\n");
            Utils.printTicket(selectedSeatNumber);

            break;
        }
    }


    public void handleSelectSeat() {
        System.out.println("\n=====[좌석 선택]=====\n");

        if (this.guest.getCurrentSeat() != NONE_SELECT) {
            System.out.println("이미 보유한 좌석이 있습니다. 좌석 이동 메뉴를 이용해 주세요.");
            return;
        }

        this.allocateSeat(SeatMode.SELECT, NONE_SELECT);
    }

    public void handleChangeSeat() {
        System.out.println("\n=====[좌석 이동]=====\n");

        if (this.guest.getCurrentSeat() == NONE_SELECT) {
            System.out.println("이미 보유한 좌석이 없습니다. 좌석 선택 메뉴를 이용해 주세요.");
            return;
        }

        int currentSeat = this.guest.getCurrentSeat();

        this.allocateSeat(SeatMode.CHANGE, currentSeat);
        this.seat.releaseSeat(currentSeat);
    }

    public void handleOrder() {
        System.out.println("음료 주문");
        System.out.println("\n=====[음료 주문]=====\n");

        if (this.guest.getCurrentSeat() == -1) {
            System.out.println("좌석이 있는 고객만 음료 주문이 가능합니다. 좌석을 먼저 선택해 주세요.");
            return;
        }

        while (true) {
            System.out.println("""
                                        구매하기 원하는 음료의 번호를 입력해 주세요.
                                        - 뒤로 돌아가기 원하신다면 0번을 입력해 주세요.
                    """);

            System.out.println("================== Jazz Club Drinks Menu ==================");
            for (int i = 0; i < menu.length; i++) {
                System.out.printf("%d. %-15s : %d원 (%s)%n", i + 1, menu[i].getName(), menu[i].getPrice(), menu[i].getDetail());
            }
            System.out.println("===========================================================");

            int menuNumber = sc.nextInt();

            if (menuNumber == 0) {
                break;
            }
            if (menuNumber < 1 || menuNumber > menu.length) {
                System.out.println("\n=====\n잘못된 입력입니다. 유효한 숫자: 1 ~ " + this.menu.length);
                System.out.println("입력한 숫자: " + menuNumber + "\n");
                continue;
            }

            Drink selectedMenu = this.menu[menuNumber - 1];
            int selectedMenuPrice = selectedMenu.getPrice();

            if (!this.guest.canSpendCash(selectedMenuPrice)) {
                System.out.println("잔액이 부족합니다. 다른 음료를 선택해 주세요.\n\n\n\n");
                continue;
            }

            this.guest.spendCash(selectedMenuPrice);
            this.cashier.addSale(selectedMenuPrice);

            System.out.println("\n🎉 음료 구매 완료! 🎉");
            System.out.println("선택한 음료: " + selectedMenu.getName() + " (" + selectedMenu.getDetail() + ")\n");

            if (selectedMenu instanceof Coffee) {
                Utils.printCoffeeASCII();
            } else if (selectedMenu instanceof Cocktail) {
                Utils.printCoffeeASCII();
            }
            return;
        }
    }

    public void handleExit() {
        System.out.println("찾아주셔서 감사합니다. 또 오세요.");

        int sales = this.cashier.getSales();
        if (sales > 0) {
            System.out.println("총 사용하신 금액: " + sales);
        }
    }

    public void run() {
        System.out.println("안녕하세요, Bluer Jazz Club입니다.");

        boolean isRunning = true;

        while (isRunning) {
            Utils.printMainMenu();

            int input = sc.nextInt();

            switch (input) {
                case 1 -> handleSelectSeat();
                case 2 -> handleChangeSeat();
                case 3 -> handleOrder();
                case 0 -> {
                    handleExit();
                    isRunning = false;
                }
                default -> System.out.println("잘못된 번호를 입력하셨습니다. 다시 입력해 주세요. (입력하신 번호: " + input + ")\n");
            }
        }
    }

    enum SeatMode {
        SELECT, CHANGE
    }
}
