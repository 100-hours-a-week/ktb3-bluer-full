package jazzclub.domain;


import java.util.Arrays;
import java.util.Scanner;

public class Seat {
    private final SeatState[] seats;
    private final Scanner sc;

    public Seat(int totalSeats, Scanner sc) {
        this.sc = sc;
        this.seats = new SeatState[totalSeats];
        Arrays.fill(this.seats, SeatState.EMPTY);

    }

    public int getTotalSeats() {
        return this.seats.length;
    }

    public boolean isSeatAvailable(int seatNumber) {
        return this.seats[seatNumber - 1] == SeatState.EMPTY;
    }

    public boolean isValidSeatNumber(int seatNumber) {
        return seatNumber >= 1 && seatNumber <= this.seats.length;
    }

    public void printIsWrongSeatNumber(int seatNumber) {
        // JazzClubView.printMessage("\n=====\n잘못된 입력입니다. 유효한 숫자: 1 ~ " + this.seats.length);
        // JazzClubView.printMessage("입력한 숫자: " + seatNumber + "\n");
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
        // JazzClubView.printMessage("\n===== 좌석 배치도 =====");

        for (int i = 0; i < seats.length; i++) {
            if (i + 1 == excludedNumber) {
            //    JazzClubView.printMessage("X ", false);
            } else if (seats[i] == SeatState.EMPTY) {
            //    JazzClubView.printMessage((i + 1) + " ", false);
            }
        }
        // JazzClubView.printMessage("");
    }


    public enum SeatState {
        EMPTY, OCCUPIED
    }
}
