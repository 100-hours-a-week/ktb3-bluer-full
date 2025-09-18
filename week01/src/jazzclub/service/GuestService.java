package jazzclub.service;

import jazzclub.domain.Guest;

public class GuestService {
    public boolean hasSeat(Guest guest) {
        return guest.hasSeat();
    }

    public void assignSeat(Guest guest, int seatNumber) {
        guest.setCurrentSeat(seatNumber);
    }

    public void releaseSeat(Guest guest) {
        guest.setCurrentSeat(Guest.NO_SEAT);
    }

    public boolean canSpendCash(Guest guest, int amount) {
        return guest.canSpendCash(amount);
    }

    public void spendCash(Guest guest, int amount) {
        guest.spendCash(amount);
    }
}