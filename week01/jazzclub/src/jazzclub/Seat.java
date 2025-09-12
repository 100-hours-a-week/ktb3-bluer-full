package jazzclub;

public class Seat {
    public int isEmptySeat = 0;
    public int isOccupiedSeat = 1;
    private int[] seats;


    public Seat(int totalSeats) {
        this.seats = new int[totalSeats];
    }

    public boolean isSeatAvailable(int seatNumber) {
        return this.seats[seatNumber - 1] == isEmptySeat;
    }

    public boolean isValidSeatNumber(int seatNumber) {
        return seatNumber >= 1 && seatNumber <= this.seats.length;
    }

    public void printIsWrongSeatNumber(int seatNumber) {
        System.out.println("\n=====\n잘못된 입력입니다. 유효한 숫자: 1 ~ " + this.seats.length);
        System.out.println("입력한 숫자: " + seatNumber + "\n");
    }

    public void occupySeat(int seatNumber) {
        if (!isValidSeatNumber(seatNumber)) {
            printIsWrongSeatNumber(seatNumber);
            return;
        }
        if (isSeatAvailable(seatNumber)) {
            this.seats[seatNumber - 1] = isOccupiedSeat;
        } else {
            System.out.println("이미 선점된 좌석입니다.");
        }
    }

    public void releaseSeat(int seatNumber) {
        if (!isValidSeatNumber(seatNumber)) {
            printIsWrongSeatNumber(seatNumber);
            return;
        }
        this.seats[seatNumber - 1] = isEmptySeat;
    }

    public void showSeatsExcluding(int currentSeat) {
        System.out.println("\n===== 좌석 배치도 =====");
        for (int i = 0; i < seats.length; i++) {
            if (i + 1 == currentSeat) {
                System.out.print("X "); // 현재 자리
            } else if (seats[i] == isEmptySeat) {
                System.out.print((i + 1) + " "); // 빈 자리
            }
        }
        System.out.println();
    }
}
