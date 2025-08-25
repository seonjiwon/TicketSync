package seonjiwon.ticketsync.domain.reservation.entity;

import jakarta.persistence.*;
import lombok.*;
import seonjiwon.ticketsync.common.entity.BaseEntity;
import seonjiwon.ticketsync.domain.performance.entity.Performance;
import seonjiwon.ticketsync.domain.performance.entity.Seat;
import seonjiwon.ticketsync.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservations")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private Performance performance;

    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    private ReservationStatus status = ReservationStatus.PENDING; // 현재 상태

    @Column(name = "total_amount")
    private int totalAmount; // 총액

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationSeat> reservationSeats = new ArrayList<>();

    public void complete(){
        this.status = ReservationStatus.COMPLETED;
    }

    public void cancel(){
        this.status = ReservationStatus.CANCELLED;
    }

    public void addSeat(Seat seat) {
        ReservationSeat reservationSeat = ReservationSeat.builder()
                .reservation(this)
                .seat(seat)
                .build();
        this.reservationSeats.add(reservationSeat);
    }
}
