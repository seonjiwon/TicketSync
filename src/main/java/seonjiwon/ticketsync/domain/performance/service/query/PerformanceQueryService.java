package seonjiwon.ticketsync.domain.performance.service.query;

import seonjiwon.ticketsync.domain.performance.dto.PerformanceDetailResponse;
import seonjiwon.ticketsync.domain.performance.dto.PerformanceListResponse;

public interface PerformanceQueryService {
    PerformanceListResponse getPerformances(String cursor);

    PerformanceDetailResponse getPerformanceDetail(Long performanceId);

}
