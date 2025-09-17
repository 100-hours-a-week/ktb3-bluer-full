package jazzclub.view;

import jazzclub.domain.Drink;

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

    public  int getMenuInput() {
        return sc.nextInt();
    }

    public void printDrinksMenu(Drink[] menu) {
        this.printMessage("================== Jazz Club Drinks Menu ==================");
        for (int i = 0; i < menu.length; i++) {
            System.out.printf("%d. %-15s : %d원 (%s)%n", i + 1, menu[i].getName(), menu[i].getPrice(), menu[i].getDetail());
        }
        this.printMessage("===========================================================");
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
}
