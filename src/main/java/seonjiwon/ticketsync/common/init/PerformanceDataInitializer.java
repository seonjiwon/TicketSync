package seonjiwon.ticketsync.common.init;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import seonjiwon.ticketsync.domain.performance.entity.Performance;
import seonjiwon.ticketsync.domain.performance.entity.Seat;
import seonjiwon.ticketsync.domain.performance.entity.SeatStatus;
import seonjiwon.ticketsync.domain.performance.entity.Section;
import seonjiwon.ticketsync.domain.performance.repository.PerformanceRepository;
import seonjiwon.ticketsync.domain.performance.repository.SeatRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Component
@RequiredArgsConstructor
@Slf4j
public class PerformanceDataInitializer {
    private final PerformanceRepository performanceRepository;
    private final SeatRepository seatRepository;

    @PostConstruct
    public void initPerformanceData() {
        if (performanceRepository.count() > 0) {
            log.info("테스트 데이터가 이미 존재합니다.");
            return;
        }

        log.info("테스트 데이터 생성 시작...");
        long startTime = System.currentTimeMillis();

        createPerformances();
        createSeatsForAllPerformances();

        long endTime = System.currentTimeMillis();
        log.info("테스트 데이터 생성 완료. 소요시간: {}ms", endTime - startTime);
    }

    private void createPerformances() {
        List<Performance> performances = List.of(
                // 대규모 동시성 테스트용 (1500석)
                Performance.builder()
                        .title("TOMORROW X TOGETHER WORLD TOUR 〈ACT : TOMORROW〉 IN SEOUL")
                        .venue("고척스카이돔")
                        .performanceDate(LocalDateTime.of(2025, 8, 22, 18, 0))
                        .build(),

                // 중간 규모 테스트용 (500석)
                Performance.builder()
                        .title("뮤지컬 〈위키드〉 내한 공연(WICKED The Musical)")
                        .venue("블루스퀘어 신한카드홀")
                        .performanceDate(LocalDateTime.of(2025, 7, 12, 19, 0))
                        .build(),

                // 동시성 테스트용 - 좌석 1개 (1000명 동시 접속용)
                Performance.builder()
                        .title("BTS 스페셜 단독석")
                        .venue("KSPO DOME")
                        .performanceDate(LocalDateTime.of(2025, 9, 11, 19, 30))
                        .build(),

                // 부하 테스트용 - 대용량 (3000석)
                Performance.builder()
                        .title("BLACKPINK 월드투어")
                        .venue("서울월드컵경기장")
                        .performanceDate(LocalDateTime.of(2025, 8, 15, 19, 0))
                        .build()
        );

        performanceRepository.saveAll(performances);
        log.info("공연 {}개 생성 완료", performances.size());
    }

    private void createSeatsForAllPerformances() {
        List<Performance> performances = performanceRepository.findAll();

        for (Performance performance : performances) {
            switch (performance.getTitle()) {
                case "TOMORROW X TOGETHER WORLD TOUR 〈ACT : TOMORROW〉 IN SEOUL":
                    createTxtSeats(performance); // 1500석
                    break;
                case "뮤지컬 〈위키드〉 내한 공연(WICKED The Musical)":
                    createWickedSeats(performance); // 500석
                    break;
                case "BTS 스페셜 단독석":
                    createSingleSeat(performance); // 1석
                    break;
                case "BLACKPINK 월드투어":
                    createBlackpinkSeats(performance); // 3000석
                    break;
            }
        }
    }

    /**
     * TXT 콘서트 좌석 생성 (1500석)
     * VIP: 300석 (1-3행, 각 100석)
     * R석: 600석 (4-9행, 각 100석)
     * S석: 600석 (10-15행, 각 100석)
     */
    private void createTxtSeats(Performance performance) {
        List<Seat> seats = new ArrayList<>();

        // VIP: 300석 (1-3행, 각 100석)
        for (int rowNo = 1; rowNo <= 3; rowNo++) {
            for (int seatNo = 1; seatNo <= 100; seatNo++) {
                seats.add(Seat.builder()
                        .performance(performance)
                        .section(Section.VIP)
                        .rowNo(rowNo)
                        .seatNo(seatNo)
                        .price(150_000)
                        .status(SeatStatus.AVAILABLE)
                        .build());

            }
        }

        // R석: 600석 (4-9행, 각 100석)
        for (int rowNo = 4; rowNo <= 9; rowNo++) {
            for (int seatNo = 1; seatNo <= 100; seatNo++) {
                seats.add(Seat.builder()
                        .performance(performance)
                        .section(Section.R)
                        .rowNo(rowNo)
                        .seatNo(seatNo)
                        .price(100_000)
                        .status(SeatStatus.AVAILABLE)
                        .build());

            }
        }

        // S석: 600석 (10-15행, 각 100석)
        for (int rowNo = 10; rowNo <= 15; rowNo++) {
            for (int seatNo = 1; seatNo <= 100; seatNo++) {
                seats.add(Seat.builder()
                        .performance(performance)
                        .section(Section.S)
                        .rowNo(rowNo)
                        .seatNo(seatNo)
                        .price(70_000)
                        .status(SeatStatus.AVAILABLE)
                        .build());

            }
        }

        seatRepository.saveAll(seats);
    }

    /**
     * 위키드 뮤지컬 좌석 생성 (500석)
     * VIP: 100석 (1-2행, 각 50석)
     * R석: 200석 (3-6행, 각 50석)
     * S석: 200석 (7-10행, 각 50석)
     */
    private void createWickedSeats(Performance performance) {
        List<Seat> seats = new ArrayList<>();

        // VIP: 100석 (1-2행, 각 50석)
        for (int rowNo = 1; rowNo <= 2; rowNo++) {
            for (int seatNo = 1; seatNo <= 50; seatNo++) {
                seats.add(Seat.builder()
                        .performance(performance)
                        .section(Section.VIP)
                        .rowNo(rowNo)
                        .seatNo(seatNo)
                        .price(150_000)
                        .status(SeatStatus.AVAILABLE)
                        .build());

            }
        }

        // R석: 200석 (3-6행, 각 50석)
        for (int rowNo = 3; rowNo <= 6; rowNo++) {
            for (int seatNo = 1; seatNo <= 50; seatNo++) {
                seats.add(Seat.builder()
                        .performance(performance)
                        .section(Section.R)
                        .rowNo(rowNo)
                        .seatNo(seatNo)
                        .price(100_000)
                        .status(SeatStatus.AVAILABLE)
                        .build());

            }
        }

        // S석: 200석 (7-10행, 각 50석)
        for (int rowNo = 7; rowNo <= 10; rowNo++) {
            for (int seatNo = 1; seatNo <= 50; seatNo++) {
                seats.add(Seat.builder()
                        .performance(performance)
                        .section(Section.S)
                        .rowNo(rowNo)
                        .seatNo(seatNo)
                        .price(70_000)
                        .status(SeatStatus.AVAILABLE)
                        .build());

            }
        }

        seatRepository.saveAll(seats);
    }

    /**
     * BTS 스페셜 단독석 (1석)
     * 동시성 테스트용 단일 좌석 (1000명이 동시에 예매할 좌석)
     */
    private void createSingleSeat(Performance performance) {
        seatRepository.save(Seat.builder()
                .performance(performance)
                .section(Section.VIP)
                .rowNo(1)
                .seatNo(1)
                .price(1000_000)
                .status(SeatStatus.AVAILABLE)
                .build());
    }

    /**
     * BLACKPINK 콘서트 좌석 생성 (3000석)
     * VIP: 500석 (1-5행, 각 100석)
     * R석: 1000석 (6-15행, 각 100석)
     * S석: 1500석 (16-30행, 각 100석)
     */
    private void createBlackpinkSeats(Performance performance) {
        List<Seat> seats = new ArrayList<>();

        // VIP: 500석 (1-5행, 각 100석)
        for (int rowNo = 1; rowNo <= 5; rowNo++) {
            for (int seatNo = 1; seatNo <= 100; seatNo++) {
                seats.add(Seat.builder()
                        .performance(performance)
                        .section(Section.VIP)
                        .rowNo(rowNo)
                        .seatNo(seatNo)
                        .price(150_000)
                        .status(SeatStatus.AVAILABLE)
                        .build());

            }
        }

        // R석: 1000석 (6-15행, 각 100석)
        for (int rowNo = 6; rowNo <= 15; rowNo++) {
            for (int seatNo = 1; seatNo <= 100; seatNo++) {
                seats.add(Seat.builder()
                        .performance(performance)
                        .section(Section.R)
                        .rowNo(rowNo)
                        .seatNo(seatNo)
                        .price(100_000)
                        .status(SeatStatus.AVAILABLE)
                        .build());

            }
        }

        // S석: 1500석 (16-30행, 각 100석)
        for (int rowNo = 16; rowNo <= 30; rowNo++) {
            for (int seatNo = 1; seatNo <= 100; seatNo++) {
                seats.add(Seat.builder()
                        .performance(performance)
                        .section(Section.S)
                        .rowNo(rowNo)
                        .seatNo(seatNo)
                        .price(70_000)
                        .status(SeatStatus.AVAILABLE)
                        .build());

            }
        }

        seatRepository.saveAll(seats);
    }
}
