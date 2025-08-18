package seonjiwon.ticketsync.domain.performance.entity;

import jakarta.persistence.*;
import lombok.*;
import seonjiwon.ticketsync.common.entity.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "performances")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class Performance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "performance_id")
    private Long id;

    private String title; // 공연 명

    private String venue; // 공연장

    @Column(name = "performance_date")
    private LocalDateTime performanceDate;
}
