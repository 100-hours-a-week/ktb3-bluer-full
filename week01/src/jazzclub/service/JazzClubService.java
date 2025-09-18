package jazzclub.service;

import jazzclub.domain.*;

public class JazzClubService {
    private final Cashier cashier;
    private final Drink[] menu;

    public JazzClubService(Cashier cashier, Drink[] menu) {
        this.cashier = cashier;
        this.menu = menu;
    }


    // Order
    public Drink orderDrink(Guest guest, int menuNumber) {
        if (menuNumber < 1 || menuNumber > menu.length) {
            throw new IllegalArgumentException(
                String.format("\n=====\n잘못된 입력입니다. 유효한 숫자: %d ~ %d\n입력한 숫자: %d\n",
                    1, this.menu.length, menuNumber
            ));
        }
        Drink selectedMenu = this.menu[menuNumber - 1];
        int selectedMenuPrice = selectedMenu.getPrice();

        if (!guest.canSpendCash(selectedMenuPrice)) {
            throw new IllegalArgumentException(
                    "잔액이 부족합니다. 다른 음료를 선택해 주세요.\n\n\n\n"
            );
        }
        guest.spendCash(selectedMenuPrice);
        cashier.addSale(selectedMenuPrice);
        return selectedMenu;
    }
}
