package seonjiwon.ticketsync.domain.performance.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seonjiwon.ticketsync.common.entity.BaseEntity;
import seonjiwon.ticketsync.domain.reservation.entity.ReservationSeat;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seats")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

    @Builder
    public Seat(Section section, int rowNo, int seatNo, int price, SeatStatus status) {
        this.section = section;
        this.rowNo = rowNo;
        this.seatNo = seatNo;
        this.price = price;
        this.status = status;
    }
}
