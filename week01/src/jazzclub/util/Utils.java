package jazzclub.util;

public class Utils {
    public static void printMainMenu() {
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
