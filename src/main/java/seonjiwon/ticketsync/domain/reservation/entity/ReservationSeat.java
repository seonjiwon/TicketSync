package seonjiwon.ticketsync.domain.reservation.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seonjiwon.ticketsync.domain.performance.entity.Seat;

@Entity
@Table(name = "reservation_seats")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReservationSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_seat_id")
    private Long id;

    // Reservation 엔티티와 다대일(N:1) 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    // Seat 엔티티와 다대일(N:1) 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @Builder
    public ReservationSeat(Reservation reservation, Seat seat) {
        setReservation(reservation);
        setSeat(seat);
    }

    private void setReservation(Reservation reservation) {
        this.reservation = reservation;
        reservation.getReservationSeats().add(this);
    }

    private void setSeat(Seat seat) {
        this.seat = seat;
        seat.getReservationSeats().add(this);
    }
}


