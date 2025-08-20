package seonjiwon.ticketsync.domain.performance.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PerformanceDetailResponse {
    private String title;
    private String venue;
    private String performanceCode;
    private LocalDateTime performanceDate;

    private int availableSeats; // 이용 가능 좌석
    private int totalSeats; // 총 좌석
}
