package jazzclub;

public class Guest {
    private int cash;
    //    TODO: 상수화
    private int currentSeat = -1;

    public Guest(int cash) {
        this.cash = cash;
    }

    private int getCash() {
        return this.cash;
    }

    public boolean canSpendCash(int amount) {
        return this.cash >= amount;
    }

    public void spendCash(int amount) {
        this.cash = this.getCash() - amount;
    }

    public int getCurrentSeat() {
        return this.currentSeat;
    }

    public void setCurrentSeat(int seatNumber) {
        this.currentSeat = seatNumber;
    }
}
