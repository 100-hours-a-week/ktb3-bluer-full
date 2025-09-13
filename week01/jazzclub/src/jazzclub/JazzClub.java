package jazzclub;

import java.util.Scanner;

public class JazzClub {
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
        System.out.println("좌석 선택");
        /*
         *   [좌석 선택]
         *   1. 좌석 선택 가능한지 확인: 이미 점유하고 있는 좌석이 있는지
         *   2. 좌석 번호 입력 받기.
         *   3. 유효한 번호라면 점유, 아니라면 불가
         **/
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
         *   1. 메뉴 출력
         *   2. 선택
         *   3. 계산
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
        System.out.println("안녕하세요, Bluer 재즈 클럽입니다.");

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
