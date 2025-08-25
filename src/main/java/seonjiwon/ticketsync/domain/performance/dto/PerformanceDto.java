package seonjiwon.ticketsync.domain.performance.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PerformanceDto {
    private Long performanceId;
    private String title;
    private String venue;
    private LocalDateTime performanceDate;
}
