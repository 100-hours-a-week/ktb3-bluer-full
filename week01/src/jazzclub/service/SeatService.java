package jazzclub.service;

import jazzclub.domain.Seat;

public class SeatService {
    public void occupySeat(Seat seat, int seatNumber) {
        seat.setSeatsState(seatNumber, Seat.SeatState.OCCUPIED);
    }

    public void releaseSeat(Seat seat, int seatNumber) {
        seat.setSeatsState(seatNumber, Seat.SeatState.EMPTY);
    }

    public boolean isSeatAvailable(Seat seat, int seatNumber) {
        return seat.isValidSeatNumber(seatNumber) && seat.isSeatAvailable(seatNumber);
    }
}
