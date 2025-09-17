package jazzclub.view;

import jazzclub.JazzClub;
import jazzclub.domain.Drink;
import jazzclub.domain.Seat;

import java.util.Scanner;

public class JazzClubView {
    private static Scanner sc;

    public JazzClubView(Scanner sc) {
        JazzClubView.sc = sc;
    }

    public  void  printMessage(String message) {
        System.out.println(message);
    }

    public  void printMessage(String message, boolean newLine) {
        if (newLine) {
            System.out.println(message);
        } else {
            System.out.print(message);
        }
    }

    public  int getInputNumber() {
        return sc.nextInt();
    }


    // Drink
    public void printDrinksMenu(Drink[] menu) {
        this.printMessage("================== Jazz Club Drinks Menu ==================");
        for (int i = 0; i < menu.length; i++) {
            System.out.printf("%d. %-15s : %d원 (%s)%n", i + 1, menu[i].getName(), menu[i].getPrice(), menu[i].getDetail());
        }
        this.printMessage("===========================================================");
    }

    public void printCoffeeASCII() {
        String cocktailASCII = """
                 ( (  ) )
                  ) (
                ........
                |      |
                |      |
                |      |
                '------'
                """;

        System.out.println(cocktailASCII);
    }

    public void printCocktailASCII() {
        String coffeeASCII = """
                   _______
                  /       \\
                 |  🍹   |
                 |       |
                 |_______|
                    | |
                    | |
                   '---'
                """;

        System.out.println(coffeeASCII);
    }


    // Seat
    public void printTicket(int selectedSeatNumber) {
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
    }

    public void showSeatsExcluding(Seat seat, int excludedNumber) {
        this.printMessage("\n===== 좌석 배치도 =====");

        for (int i = 0; i < seat.getTotalSeats(); i++) {
            if (i + 1 == excludedNumber) {
                this.printMessage("X ", false);
            } else if (seat.isSeatAvailable(i + 1)) {
                this.printMessage((i + 1) + " ", false);
            }
        }

        this.printMessage("\n");
    }

    public void printIsWrongSeatNumber(int seatNumber, int totalSeatsLength) {
         this.printMessage("\n=====\n잘못된 입력입니다. 유효한 숫자: 1 ~ " + totalSeatsLength);
         this.printMessage("입력한 숫자: " + seatNumber + "\n");
    }

    public void printMainMenu() {
        this.printMessage(
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
    }

}
