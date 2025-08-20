package seonjiwon.ticketsync.domain.performance.service;

import seonjiwon.ticketsync.domain.performance.dto.PerformanceDetailResponse;
import seonjiwon.ticketsync.domain.performance.dto.PerformanceCreateRequest;
import seonjiwon.ticketsync.domain.performance.dto.PerformanceListResponse;

public interface PerformanceService {

    PerformanceListResponse getPerformances(String cursor);

    PerformanceDetailResponse getPerformanceDetail(String performanceCode);

    String createPerformance(PerformanceCreateRequest request);
}
