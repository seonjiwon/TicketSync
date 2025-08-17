package seonjiwon.ticketsync.domain.performance.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seonjiwon.ticketsync.common.entity.BaseEntity;
import seonjiwon.ticketsync.domain.reservation.entity.Reservation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "performances")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Performance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "performance_id")
    private Long id;

    private String title; // 공연 명

    private String venue; // 공연장

    @Column(name = "performance_date")
    private LocalDateTime performanceDate;

    @OneToMany(mappedBy = "performance")
    private List<Reservation> reservations = new ArrayList<>();

    @Builder
    public Performance(String title, String venue, LocalDateTime performanceDate) {
        this.title = title;
        this.venue = venue;
        this.performanceDate = performanceDate;
    }
}
