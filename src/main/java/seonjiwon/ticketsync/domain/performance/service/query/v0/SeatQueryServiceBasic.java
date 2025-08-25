package seonjiwon.ticketsync.domain.performance.service.query.v0;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seonjiwon.ticketsync.common.exception.CustomException;
import seonjiwon.ticketsync.domain.performance.converter.SeatConverter;
import seonjiwon.ticketsync.domain.performance.dto.SeatResponse;
import seonjiwon.ticketsync.domain.performance.entity.Performance;
import seonjiwon.ticketsync.domain.performance.entity.Seat;
import seonjiwon.ticketsync.domain.performance.entity.Section;
import seonjiwon.ticketsync.domain.performance.exception.PerformanceErrorCode;
import seonjiwon.ticketsync.domain.performance.repository.PerformanceRepository;
import seonjiwon.ticketsync.domain.performance.repository.SeatRepository;
import seonjiwon.ticketsync.domain.performance.service.query.SeatQueryService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SeatQueryServiceBasic implements SeatQueryService {

    private final SeatRepository seatRepository;
    private final PerformanceRepository performanceRepository;

    @Override
    public SeatResponse getSeats(Long performanceId) {
        Performance performance = performanceRepository.findById(performanceId)
                .orElseThrow(() -> new CustomException(PerformanceErrorCode.PERFORMANCE_NOT_FOUND));

        // 공연에 해당하는 모든 Seat 정보를 가져옴
        List<Seat> seats = seatRepository.findAllByPerformance(performance);


        return SeatConverter.toSeatResponse(performance, seats);
    }
}
