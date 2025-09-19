package jazzclub.service;

import jazzclub.domain.Guest;

public class GuestService {
    public boolean hasSeat(Guest guest) {
        return guest.getCurrentSeat() != Guest.NO_SEAT;
    }

    public void assignSeat(Guest guest, int seatNumber) {
        guest.setCurrentSeat(seatNumber);
    }

    public void releaseSeat(Guest guest) {
        guest.setCurrentSeat(Guest.NO_SEAT);
    }

    public boolean canSpendCash(Guest guest, int amount) {
        return guest.getCash() >= amount;
    }

    public void spendCash(Guest guest, int amount) {
        guest.setCash(guest.getCash() - amount);
    }
}
