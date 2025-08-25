package seonjiwon.ticketsync.domain.performance.service.query.v0;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seonjiwon.ticketsync.common.exception.CustomException;
import seonjiwon.ticketsync.domain.performance.converter.PerformanceConverter;
import seonjiwon.ticketsync.domain.performance.dto.PerformanceDetailResponse;
import seonjiwon.ticketsync.domain.performance.dto.PerformanceDto;
import seonjiwon.ticketsync.domain.performance.dto.PerformanceListResponse;
import seonjiwon.ticketsync.domain.performance.entity.Performance;
import seonjiwon.ticketsync.domain.performance.entity.SeatStatus;
import seonjiwon.ticketsync.domain.performance.exception.PerformanceErrorCode;
import seonjiwon.ticketsync.domain.performance.repository.PerformanceRepository;
import seonjiwon.ticketsync.domain.performance.repository.SeatRepository;
import seonjiwon.ticketsync.domain.performance.service.query.PerformanceQueryService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PerformanceQueryServiceBasic implements PerformanceQueryService {
    private final PerformanceRepository performanceRepository;
    private final SeatRepository seatRepository;

    private static final int DEFAULT_PAGE_SIZE = 10;

    @Override
    public PerformanceListResponse getPerformances(String cursor) {
        Pageable pageable = PageRequest.of(0, DEFAULT_PAGE_SIZE + 1);
        List<Performance> performances;

        // Cursor 페이징으로 Performance 가져오기
        performances = getPerformancesWithCursorPaging(cursor, pageable);

        // 추가로 불러온 마지막 제거
        boolean hasMore = performances.size() > DEFAULT_PAGE_SIZE;
        if (hasMore) {
            performances.remove(performances.size() - 1);
        }
        LocalDateTime nextCursor = hasMore ? performances.get(performances.size() - 1).getCreatedAt() : null;


        List<PerformanceDto> performanceDtos = performances.stream()
                .map(PerformanceConverter::toPerformanceDto)
                .toList();

        return PerformanceConverter.toPerformanceListResponse(performanceDtos, nextCursor);
    }

    @Override
    public PerformanceDetailResponse getPerformanceDetail(Long performanceId) {
        log.info("공연 단건 조회를 시작합니다. performanceCode: {}", performanceId);

        Performance performance = performanceRepository.findById(performanceId)
                .orElseThrow(() -> new CustomException(PerformanceErrorCode.PERFORMANCE_NOT_FOUND));

        int totalSeatCount = seatRepository.countByPerformance(performance);
        int availableSeatCount = seatRepository.countByPerformanceAndStatus(performance, SeatStatus.AVAILABLE);

        return PerformanceConverter.toPerformanceDetailResponse(performance, totalSeatCount, availableSeatCount);
    }

    private List<Performance> getPerformancesWithCursorPaging(String cursor, Pageable pageable) {
        List<Performance> performances;
        if (cursor == null || cursor.isEmpty()) {
            performances = performanceRepository.findByCursorDefault(pageable);
        } else {
            performances = performanceRepository.findByCursor(LocalDateTime.parse(cursor), pageable);
        }
        return performances;
    }
}
