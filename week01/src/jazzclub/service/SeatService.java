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
        return isValidSeatNumber(seat, seatNumber) && seat.getSeats()[seatNumber - 1] == Seat.SeatState.EMPTY;
    }

    public boolean isValidSeatNumber(Seat seat, int seatNumber) {
        return seatNumber >= 1 && seatNumber <= seat.getTotalSeats();
    }

    public Seat.SeatState getSeatState(Seat seat, int seatNumber) {
        return seat.getSeats()[seatNumber - 1];
    }
}
