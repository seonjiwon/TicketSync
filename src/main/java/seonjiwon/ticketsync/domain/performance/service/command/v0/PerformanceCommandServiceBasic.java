package seonjiwon.ticketsync.domain.performance.service.command.v0;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seonjiwon.ticketsync.domain.performance.converter.PerformanceConverter;
import seonjiwon.ticketsync.domain.performance.converter.SeatConverter;
import seonjiwon.ticketsync.domain.performance.dto.PerformanceCreateRequest;
import seonjiwon.ticketsync.domain.performance.dto.SectionConfig;
import seonjiwon.ticketsync.domain.performance.entity.Performance;
import seonjiwon.ticketsync.domain.performance.entity.Seat;
import seonjiwon.ticketsync.domain.performance.repository.PerformanceRepository;
import seonjiwon.ticketsync.domain.performance.repository.SeatRepository;
import seonjiwon.ticketsync.domain.performance.service.command.PerformanceCommandService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PerformanceCommandServiceBasic implements PerformanceCommandService {

    private final PerformanceRepository performanceRepository;
    private final SeatRepository seatRepository;


    @Override
    public Long createPerformance(PerformanceCreateRequest request) {
        log.info("공연 데이터 생성 시작...");
        long startTime = System.currentTimeMillis();

        Performance performance = convertRequestToPerformances(request);
        createSeatsForPerformances(request, performance);

        long endTime = System.currentTimeMillis();
        log.info("공연 데이터 생성 완료. 소요시간: {}ms", endTime - startTime);

        return performance.getId();
    }

    private Performance convertRequestToPerformances(PerformanceCreateRequest request) {
        Performance performance = PerformanceConverter.of(request);
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
                seats.add(SeatConverter.of(performance, config, rowNo, seatNo));
            }
        }
        return seats;
    }
}

