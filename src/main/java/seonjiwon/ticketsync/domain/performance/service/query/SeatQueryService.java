package seonjiwon.ticketsync.domain.performance.service.query;

import seonjiwon.ticketsync.domain.performance.dto.SeatResponse;

public interface SeatQueryService {
    SeatResponse getSeats(Long performanceId);
}
