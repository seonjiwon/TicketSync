package seonjiwon.ticketsync.domain.performance.entity;

import jakarta.persistence.*;
import lombok.*;
import seonjiwon.ticketsync.common.entity.BaseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

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

    private String performanceCode = UUID.randomUUID().toString();

    @Column(name = "performance_date")
    private LocalDateTime performanceDate;

    @Builder
    public Performance(String title, String venue, LocalDateTime performanceDate) {
        this.title = title;
        this.venue = venue;
        this.performanceDate = performanceDate;
    }
}
