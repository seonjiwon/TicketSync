package seonjiwon.ticketsync.domain.performance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class PerformanceInfoDto {
    private String title;
    private String venue;
    private String performanceCode;
    private LocalDateTime performanceDate;
}
