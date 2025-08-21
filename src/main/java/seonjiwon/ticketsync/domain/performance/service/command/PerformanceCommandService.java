package seonjiwon.ticketsync.domain.performance.service.command;

import seonjiwon.ticketsync.domain.performance.dto.PerformanceDetailResponse;
import seonjiwon.ticketsync.domain.performance.dto.PerformanceCreateRequest;
import seonjiwon.ticketsync.domain.performance.dto.PerformanceListResponse;

public interface PerformanceCommandService {
    String createPerformance(PerformanceCreateRequest request);
}
