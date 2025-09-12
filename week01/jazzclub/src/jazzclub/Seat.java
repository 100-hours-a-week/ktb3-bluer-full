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

    public void occupySeat(int seatNumber) {
        if (isSeatAvailable(seatNumber)) {
            this.seats[seatNumber - 1] = isOccupiedSeat;
        } else {
            System.out.println("이미 선점된 좌석입니다.");
        }
    }

    public void releaseSeat(int seatNumber) {
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
