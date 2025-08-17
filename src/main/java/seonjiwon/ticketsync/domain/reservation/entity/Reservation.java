package seonjiwon.ticketsync.domain.reservation.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seonjiwon.ticketsync.common.entity.BaseEntity;
import seonjiwon.ticketsync.domain.performance.entity.Performance;
import seonjiwon.ticketsync.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservations")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

    @Enumerated(value = EnumType.STRING)
    private ReservationStatus status; // 현재 상태

    @Column(name = "total_amount")
    private int totalAmount; // 총액

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationSeat> reservationSeats = new ArrayList<>();

    @Builder
    public Reservation(User user, Performance performance, ReservationStatus status, int totalAmount) {
        this.user = user;
        this.performance = performance;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    public void setUser(User user) {
        this.user = user;
        user.getReservations().add(this);
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
        performance.getReservations().add(this);
    }
}
