package jazzclub.domain;


import java.util.Arrays;

public class Seat {
    private final SeatState[] seats;

    public Seat(int totalSeats) {
        this.seats = new SeatState[totalSeats];
        Arrays.fill(this.seats, SeatState.EMPTY);

    }

    public int getTotalSeats() {
        return this.seats.length;
    }

    public SeatState[] getSeats() {
        return seats;
    }

    public void setSeatsState(int seatNumber, SeatState state) {
        this.seats[seatNumber - 1] = state;
    }


    public enum SeatState {
        EMPTY, OCCUPIED
    }
}
