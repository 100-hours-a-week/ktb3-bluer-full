package jazzclub.view;

import jazzclub.domain.Drink;

import java.util.Scanner;

public class JazzClubView {
    private static Scanner sc;

    public JazzClubView(Scanner sc) {
        JazzClubView.sc = sc;
    }

    public static void  printMessage(String message) {
        System.out.println(message);
    }

    public static void printMessage(String message, boolean newLine) {
        if (newLine) {
            System.out.println(message);
        } else {
            System.out.print(message);
        }
    }

    public static int getMenuInput() {
        return sc.nextInt();
    }

    public static void printDrinksMenu(Drink[] menu) {
        printMessage("================== Jazz Club Drinks Menu ==================");
        for (int i = 0; i < menu.length; i++) {
            System.out.printf("%d. %-15s : %d원 (%s)%n", i + 1, menu[i].getName(), menu[i].getPrice(), menu[i].getDetail());
        }
        printMessage("===========================================================");
    }

    public static void printMainMenu() {
        printMessage(
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

    public static void printTicket(int selectedSeatNumber) {
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

    public static void printCoffeeASCII() {
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

    public static void printCocktailASCII() {
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
}
