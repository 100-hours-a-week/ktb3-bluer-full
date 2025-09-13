package jazzclub;

import java.util.Scanner;

public class JazzClub {
    private final int NONE_SELECT = -1;
    private Guest guest;
    private Seat seat;
    private Cashier cashier;
    private Drink[] menu;
    private Scanner sc;

    public JazzClub(Guest guest, Seat seat, Cashier cashier, Drink[] menu, Scanner sc) {
        this.guest = guest;
        this.seat = seat;
        this.cashier = cashier;
        this.menu = menu;
        this.sc = sc;
    }


    public void handleSelectSeat() {
        System.out.println("\n=====[좌석 선택]=====\n");

        if (this.guest.getCurrentSeat() != NONE_SELECT) {
            System.out.println("이미 보유한 좌석이 있습니다. 좌석 이동 메뉴를 이용해 주세요.");
            return;
        }

        while (true) {
            System.out.println("원하는 좌석의 번호를 입력해 주세요.\n");
            this.seat.showSeatsExcluding(NONE_SELECT);

            int selectedSeatNumber = sc.nextInt();

            if (!this.seat.isValidSeatNumber(selectedSeatNumber)) {
                this.seat.printIsWrongSeatNumber(selectedSeatNumber);
                continue;
            }
            if (!this.seat.isSeatAvailable(selectedSeatNumber)) {
                System.out.println("이미 선점된 좌석입니다.");
                continue;
            }

            this.seat.occupySeat(selectedSeatNumber);
            System.out.println("좌석 선택이 완료되었습니다. 발급받은 입장권을 갖고 들어가 주세요.\n\n");
            String ticket = String.format("""
                    =====================================
                    |               Ticket              |
                    |-----------------------------------|
                    | 좌석: %d번                            |
                    |-----------------------------------|
                    |  <Bluer Jazz Club>                |
                    =====================================
                    """, selectedSeatNumber);

            System.out.println(ticket);
            this.guest.setCurrentSeat(selectedSeatNumber);
            break;
        }
    }


    public void handleChangeSeat() {
        System.out.println("좌석 이동");
        /*
         *   [좌석 이동]
         *   1. 이미 점유하고 있는 좌석이 있는지 확인 -> 없다면 에러 메시지
         *   2. 현재 좌석을 제외한 가능한 좌석 보여주기
         *   3. 원하는 좌석 번호 입력 받기
         *   4. 기존 좌석 해제
         *   5. 좌석 점유
         * */
    }

    public void handleOrder() {
        System.out.println("음료 주문");
        /*
         *   [음료 주문]
         *   1. 점유하고 있는 좌석이 있는지 확인 -> 없다면 에러: "좌석이 있는 고객님만 주문이 가능합니다."
         *   2. 메뉴 출력
         *   3. 선택
         *   4. 계산
         */
    }

    public void handleExit() {
        System.out.println("퇴장");
        /*
         * [퇴장]
         *
         * 오늘의 매출 출력
         */
    }

    public void run() {
        System.out.println("안녕하세요, Bluer Jazz Club입니다.");

        boolean isRunning = true;

        while (isRunning) {
            System.out.println(
                    """
                                                        
                            -------------------
                            원하시는 동작을 입력하세요
                                    
                            (1) 좌석 선택
                            (2) 좌석 이동
                            (3) 음료 주문
                                
                            (0) 퇴장
                            -------------------
                                                        
                            """
            );

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
}
