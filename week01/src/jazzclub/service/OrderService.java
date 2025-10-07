package jazzclub.service;

import jazzclub.domain.Cashier;
import jazzclub.domain.Drink;
import jazzclub.domain.Guest;

public class OrderService {
    private final GuestService guestService;

    public OrderService(GuestService guestService) {
        this.guestService = guestService;
    }

    public Drink orderDrink(Guest guest, Drink[] menu, int menuNumber) {
        if (menuNumber < 1 || menuNumber > menu.length) {
             throw new IllegalArgumentException(
                String.format("\n=====\n잘못된 입력입니다. 유효한 숫자: %d ~ %d\n입력한 숫자: %d\n",
                    1, menu.length, menuNumber
            ));
        }

        Drink selected = menu[menuNumber - 1];

        if (!guestService.canSpendCash(guest, selected.getPrice())) {
            throw new IllegalArgumentException(
                    "잔액이 부족합니다. 다른 음료를 선택해 주세요.\n\n\n\n"
            );
        }
        guestService.spendCash(guest, selected.getPrice());

        return selected;
    }

    public void addSale(Cashier cashier, int amount) {
        cashier.addSale(amount);
    }
}
