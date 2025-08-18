package seonjiwon.ticketsync.domain.reservation.entity;

import jakarta.persistence.*;
import lombok.*;
import seonjiwon.ticketsync.domain.performance.entity.Seat;

@Entity
@Table(name = "reservation_seats")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    public static ReservationSeat create(Reservation reservation, Seat seat) {
        return ReservationSeat.builder()
                .reservation(reservation)
                .seat(seat)
                .build();
    }
}


