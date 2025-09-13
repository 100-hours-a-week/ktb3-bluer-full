package jazzclub;

import java.util.Arrays;

public class Seat {
    private final SeatState[] seats;

    public Seat(int totalSeats) {
        this.seats = new SeatState[totalSeats];
        Arrays.fill(this.seats, SeatState.EMPTY);
    }

    public boolean isSeatAvailable(int seatNumber) {
        return this.seats[seatNumber - 1] == SeatState.EMPTY;
    }

    public boolean isValidSeatNumber(int seatNumber) {
        return seatNumber >= 1 && seatNumber <= this.seats.length;
    }

    public void printIsWrongSeatNumber(int seatNumber) {
        System.out.println("\n=====\n잘못된 입력입니다. 유효한 숫자: 1 ~ " + this.seats.length);
        System.out.println("입력한 숫자: " + seatNumber + "\n");
    }

    public void occupySeat(int seatNumber) {
        this.seats[seatNumber - 1] = SeatState.OCCUPIED;
    }

    public void releaseSeat(int seatNumber) {
        if (!isValidSeatNumber(seatNumber)) {
            printIsWrongSeatNumber(seatNumber);
            return;
        }
        this.seats[seatNumber - 1] = SeatState.EMPTY;
    }

    public void showSeatsExcluding(int excludedNumber) {
        System.out.println("\n===== 좌석 배치도 =====");

        for (int i = 0; i < seats.length; i++) {
            if (i + 1 == excludedNumber) {
                System.out.print("X ");
            } else if (seats[i] == SeatState.EMPTY) {
                System.out.print((i + 1) + " ");
            }
        }
        System.out.println();
    }

    enum SeatState {
        EMPTY, OCCUPIED
    }
}
