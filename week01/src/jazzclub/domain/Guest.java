package jazzclub.domain;

import jazzclub.util.Constants;

public class Guest {
    static public final int NO_SEAT = Constants.Seat.NO_SEAT;
    private int cash;
    private int currentSeat = NO_SEAT;

    public Guest(int cash) {
        this.cash = cash;
    }

    public int getCash() {
        return this.cash;
    }

    public int getCurrentSeat() {
        return this.currentSeat;
    }

    public void setCurrentSeat(int seatNumber) {
        this.currentSeat = seatNumber;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }
}
