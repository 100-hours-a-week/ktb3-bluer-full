package jazzclub.domain;

import jazzclub.JazzClub;
import jazzclub.util.Utils;

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

    public void allocateSeat(JazzClub.SeatMode mode, int excludedSeat, Guest guest) {
        boolean seatSelected = false;

        while (!seatSelected) {
            System.out.println("원하는 좌석의 번호를 입력해 주세요.\n");
            System.out.println("뒤로 돌아가기 원하신다면 0번을 입력해 주세요.\n");
            this.showSeatsExcluding(excludedSeat);

            int selectedSeatNumber = sc.nextInt();

            if (selectedSeatNumber == 0) {
                return;
            }

            if (mode.equals(JazzClub.SeatMode.CHANGE) && selectedSeatNumber == excludedSeat) {
                System.out.println("현재 좌석과 동일한 좌석입니다.");
                continue;
            }
            if (!this.isValidSeatNumber(selectedSeatNumber)) {
                this.printIsWrongSeatNumber(selectedSeatNumber);
                continue;
            }
            if (!this.isSeatAvailable(selectedSeatNumber)) {
                System.out.println("이미 선점된 좌석입니다.");
                continue;
            }

            this.occupySeat(selectedSeatNumber);
            guest.setCurrentSeat(selectedSeatNumber);

            System.out.println("좌석 선택이 완료되었습니다. 발급받은 입장권을 갖고 들어가 주세요.\n\n");
            Utils.printTicket(selectedSeatNumber);

            seatSelected = true;
        }
    }

    enum SeatState {
        EMPTY, OCCUPIED
    }
}
