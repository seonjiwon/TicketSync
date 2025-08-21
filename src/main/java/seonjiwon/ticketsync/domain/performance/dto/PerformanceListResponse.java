package seonjiwon.ticketsync.domain.performance.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PerformanceListResponse {
    private List<PerformanceDto> performanceInfos;
    private String nextCursor; // 다음 커서 위치
}
