package seonjiwon.ticketsync.domain.performance.service.v0;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seonjiwon.ticketsync.common.exception.CustomException;
import seonjiwon.ticketsync.domain.performance.dto.*;
import seonjiwon.ticketsync.domain.performance.entity.Performance;
import seonjiwon.ticketsync.domain.performance.entity.Seat;
import seonjiwon.ticketsync.domain.performance.entity.SeatStatus;
import seonjiwon.ticketsync.domain.performance.exception.PerformanceErrorCode;
import seonjiwon.ticketsync.domain.performance.repository.PerformanceRepository;
import seonjiwon.ticketsync.domain.performance.repository.SeatRepository;
import seonjiwon.ticketsync.domain.performance.service.PerformanceService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PerformanceServiceBasic implements PerformanceService {

    private static final int DEFAULT_PAGE_SIZE = 10;

    private final PerformanceRepository performanceRepository;
    private final SeatRepository seatRepository;



    @Override
    public PerformanceListResponse getPerformances(String cursor) {
        Pageable pageable = PageRequest.of(0, DEFAULT_PAGE_SIZE + 1);
        List<Performance> performances;

        if (cursor == null || cursor.isEmpty()) {
            performances = performanceRepository.findByCursorDefault(pageable);
        } else {
            performances = performanceRepository.findByCursor(Long.parseLong(cursor), pageable);
        }

        // 추가로 불러온 마지막 제거
        boolean hasMore = performances.size() > DEFAULT_PAGE_SIZE;
        if (hasMore) {
            performances.remove(performances.size() - 1);
        }

        List<PerformanceInfoDto> performanceInfos = new ArrayList<>();
        for (Performance performance : performances) {
            performanceInfos.add(PerformanceInfoDto.builder()
                    .title(performance.getTitle())
                    .venue(performance.getVenue())
                    .performanceCode(performance.getPerformanceCode())
                    .performanceDate(performance.getPerformanceDate())
                    .build());
        }

        String nextCursor = hasMore ? performances.get(performances.size() - 1).getId().toString() : null;

        return PerformanceListResponse.builder()
                .performanceInfos(performanceInfos)
                .nextCursor(nextCursor)
                .build();
    }

    @Override
    public PerformanceDetailResponse getPerformanceDetail(String performanceCode) {
        log.info("공연 단건 조회를 시작합니다. performanceCode: {}", performanceCode);
        Performance performance = performanceRepository.findByPerformanceCode(performanceCode)
                .orElseThrow(() -> new CustomException(PerformanceErrorCode.PERFORMANCE_NOT_FOUND));

        int totalSeatCount = seatRepository.countByPerformance(performance);
        int availableSeatCount = seatRepository.countByPerformanceAndStatus(performance, SeatStatus.AVAILABLE);

        return PerformanceDetailResponse.builder()
                .title(performance.getTitle())
                .venue(performance.getVenue())
                .performanceCode(performanceCode)
                .performanceDate(performance.getPerformanceDate())
                .totalSeats(totalSeatCount)
                .availableSeats(availableSeatCount)
                .build();
    }

    @Override
    @Transactional
    public String createPerformance(PerformanceCreateRequest request) {
        log.info("공연 데이터 생성 시작...");
        long startTime = System.currentTimeMillis();

        Performance performance = convertRequestToPerformances(request);
        createSeatsForPerformances(request, performance);

        long endTime = System.currentTimeMillis();
        log.info("공연 데이터 생성 완료. 소요시간: {}ms", endTime - startTime);

        return performance.getPerformanceCode();
    }

    private Performance convertRequestToPerformances(PerformanceCreateRequest request) {
        Performance performance = Performance.builder()
                .title(request.getTitle())
                .venue(request.getVenue())
                .performanceDate(request.getPerformanceDate())
                .build();

        return performanceRepository.save(performance);
    }

    private void createSeatsForPerformances(PerformanceCreateRequest request, Performance performance) {

        List<Seat> seats = request.getSectionConfigs().stream()
                .map(config -> createSeatsForSection(performance, config))  // List<Seat> 반환
                .flatMap(List::stream)  // List<Seat>들을 하나의 Stream<Seat>로 평탄화
                .toList();

        seatRepository.saveAll(seats);
    }

    private List<Seat> createSeatsForSection(Performance performance, SectionConfig config) {
        List<Seat> seats = new ArrayList<>();

        for (int rowNo = config.getStartRow(); rowNo <= config.getEndRow(); rowNo++) {
            for (int seatNo = 1; seatNo <= config.getSeatsPerRow(); seatNo++) {
                seats.add(Seat.builder()
                        .performance(performance)
                        .section(config.getSection())
                        .rowNo(rowNo)
                        .seatNo(seatNo)
                        .price(config.getPrice())
                        .status(SeatStatus.AVAILABLE)
                        .build());
            }
        }

        return seats;
    }


}

