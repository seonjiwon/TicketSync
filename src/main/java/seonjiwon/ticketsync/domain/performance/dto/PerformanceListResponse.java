package seonjiwon.ticketsync.domain.performance.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PerformanceListResponse {
    private List<PerformanceDto> performanceInfos;
    private LocalDateTime nextCursor; // 다음 커서 위치
}
