package jazzclub.domain;

import jazzclub.JazzClub;
import jazzclub.view.JazzClubView;

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
        JazzClubView.printMessage("\n=====\n잘못된 입력입니다. 유효한 숫자: 1 ~ " + this.seats.length);
        JazzClubView.printMessage("입력한 숫자: " + seatNumber + "\n");
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
        JazzClubView.printMessage("\n===== 좌석 배치도 =====");

        for (int i = 0; i < seats.length; i++) {
            if (i + 1 == excludedNumber) {
               JazzClubView.printMessage("X ");
            } else if (seats[i] == SeatState.EMPTY) {
               JazzClubView.printMessage((i + 1) + " ");
            }
        }
    }

    public void allocateSeat(JazzClub.SeatMode mode, int excludedSeat, Guest guest) {
        boolean seatSelected = false;

        while (!seatSelected) {
            JazzClubView.printMessage("원하는 좌석의 번호를 입력해 주세요.\n");
            JazzClubView.printMessage("뒤로 돌아가기 원하신다면 0번을 입력해 주세요.\n");
            this.showSeatsExcluding(excludedSeat);

            int selectedSeatNumber = sc.nextInt();

            if (selectedSeatNumber == 0) {
                return;
            }

            if (mode.equals(JazzClub.SeatMode.CHANGE) && selectedSeatNumber == excludedSeat) {
                JazzClubView.printMessage("현재 좌석과 동일한 좌석입니다.");
                continue;
            }
            if (!this.isValidSeatNumber(selectedSeatNumber)) {
                this.printIsWrongSeatNumber(selectedSeatNumber);
                continue;
            }
            if (!this.isSeatAvailable(selectedSeatNumber)) {
                JazzClubView.printMessage("이미 선점된 좌석입니다.");
                continue;
            }

            this.occupySeat(selectedSeatNumber);
            guest.setCurrentSeat(selectedSeatNumber);

            JazzClubView.printMessage("좌석 선택이 완료되었습니다. 발급받은 입장권을 갖고 들어가 주세요.\n\n");
            JazzClubView.printTicket(selectedSeatNumber);

            seatSelected = true;
        }
    }

    enum SeatState {
        EMPTY, OCCUPIED
    }
}
