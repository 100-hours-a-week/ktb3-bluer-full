package jazzclub;

public class Guest {
    private int cash;
    private int currentSeat = -1;

    public Guest(int cash) {
        this.cash = cash;
    }

    public int getCash() {
        return this.cash;
    }

    public boolean canSpendCash(int amount) {
        return this.cash >= amount;
    }

    public void spendCash(int amount) {
        this.cash = this.cash - amount;
        // TODO: 지불 예정 비용보다 보유 현금이 적은 경우 처리
    }

    public int getCurrentSeat() {
        return this.currentSeat;
    }

    public void setCurrentSeat(int seatNumber) {
        this.currentSeat = seatNumber;
    }
}
