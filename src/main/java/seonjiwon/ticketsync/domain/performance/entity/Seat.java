package seonjiwon.ticketsync.domain.performance.entity;

import jakarta.persistence.*;
import lombok.*;
import seonjiwon.ticketsync.common.entity.BaseEntity;
import seonjiwon.ticketsync.domain.reservation.entity.ReservationSeat;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seats")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Builder
public class Seat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private Performance performance;

    @Enumerated(value = EnumType.STRING)
    private Section section; // 구역(VIP/R/S)

    @Column(name = "row_no")
    private int rowNo; // 행 번호

    @Column(name = "seat_no")
    private int seatNo; // 좌석 번호

    private int price;

    @Enumerated(value = EnumType.STRING)
    private SeatStatus status; // 좌석 상태
}
