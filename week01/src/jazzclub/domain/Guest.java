package jazzclub.domain;

public class Guest {
    public final int NO_SEAT = -1;
    private int cash;
    private int currentSeat = NO_SEAT;

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

    public boolean hasSeat() {
        return this.currentSeat != NO_SEAT;
    }
}
