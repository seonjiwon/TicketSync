package seonjiwon.ticketsync.common.init;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import seonjiwon.ticketsync.domain.performance.dto.PerformanceCreateRequest;
import seonjiwon.ticketsync.domain.performance.dto.SectionConfig;
import seonjiwon.ticketsync.domain.performance.entity.Section;
import seonjiwon.ticketsync.domain.performance.repository.PerformanceRepository;
import seonjiwon.ticketsync.domain.performance.service.command.PerformanceCommandService;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PerformanceDataInitializer {
    private final PerformanceRepository performanceRepository;
    private final PerformanceCommandService performanceService;

    @PostConstruct
    public void initPerformanceData() {
        if (performanceRepository.count() > 0) {
            log.info("테스트 데이터가 이미 존재합니다.");
            return;
        }

        log.info("테스트 데이터 생성 시작...");
        long startTime = System.currentTimeMillis();

        createPerformances();

        long endTime = System.currentTimeMillis();
        log.info("테스트 데이터 생성 완료. 소요시간: {}ms", endTime - startTime);
    }

    private void createPerformances() {
        List<PerformanceCreateRequest> requests = List.of(
                // K-POP 콘서트 (4개)
                PerformanceCreateRequest.builder()
                        .title("2025 NewJeans Fan Meeting [How Sweet] in Seoul")
                        .venue("올림픽공원 KSPO DOME")
                        .performanceDate(LocalDateTime.of(2025, 8, 15, 19, 0))
                        .sectionConfigs(createMediumVenueConfig())
                        .build(),

                PerformanceCreateRequest.builder()
                        .title("IU 2025 CONCERT [The Golden Hour] in Seoul")
                        .venue("서울월드컵경기장")
                        .performanceDate(LocalDateTime.of(2025, 10, 5, 19, 0))
                        .sectionConfigs(createLargeVenueConfig())
                        .build(),

                PerformanceCreateRequest.builder()
                        .title("2025 (G)I-DLE WORLD TOUR [I am FREE-TY] in Seoul")
                        .venue("잠실실내체육관")
                        .performanceDate(LocalDateTime.of(2025, 7, 12, 19, 30))
                        .sectionConfigs(createMediumVenueConfig())
                        .build(),

                PerformanceCreateRequest.builder()
                        .title("2025 LE SSERAFIM CONCERT [UNFORGIVEN] in Seoul")
                        .venue("올림픽체조경기장")
                        .performanceDate(LocalDateTime.of(2025, 6, 28, 19, 30))
                        .sectionConfigs(createSmallVenueConfig())
                        .build(),

                // 뮤지컬 (3개) - 위키드 제외
                PerformanceCreateRequest.builder()
                        .title("뮤지컬 〈알라딘〉 한국 초연 (ALADDIN The Musical)")
                        .venue("샤롯데씨어터")
                        .performanceDate(LocalDateTime.of(2025, 4, 22, 19, 30))
                        .sectionConfigs(createTheaterConfig())
                        .build(),

                PerformanceCreateRequest.builder()
                        .title("뮤지컬 〈지킬앤하이드〉 20주년 (Jekyll & Hyde)")
                        .venue("블루스퀘어 신한카드홀")
                        .performanceDate(LocalDateTime.of(2025, 3, 29, 19, 30))
                        .sectionConfigs(createTheaterConfig())
                        .build(),

                PerformanceCreateRequest.builder()
                        .title("뮤지컬 〈팬텀〉 10주년 기념공연")
                        .venue("세종문화회관 대극장")
                        .performanceDate(LocalDateTime.of(2025, 6, 15, 19, 30))
                        .sectionConfigs(createConcertHallConfig())
                        .build(),

                // 내한 콘서트 (3개)
                PerformanceCreateRequest.builder()
                        .title("Coldplay Music of the Spheres World Tour Seoul")
                        .venue("인천 인스파이어 아레나")
                        .performanceDate(LocalDateTime.of(2025, 4, 16, 19, 30))
                        .sectionConfigs(createLargeVenueConfig())
                        .build(),

                PerformanceCreateRequest.builder()
                        .title("Ed Sheeran +-=÷x Tour Seoul")
                        .venue("고척스카이돔")
                        .performanceDate(LocalDateTime.of(2025, 5, 8, 19, 0))
                        .sectionConfigs(createLargeVenueConfig())
                        .build(),

                PerformanceCreateRequest.builder()
                        .title("Bruno Mars 24K Magic World Tour Seoul")
                        .venue("잠실실내체육관")
                        .performanceDate(LocalDateTime.of(2025, 11, 22, 19, 30))
                        .sectionConfigs(createMediumVenueConfig())
                        .build(),

                // 트로트/발라드 (2개) - 임영웅 제외
                PerformanceCreateRequest.builder()
                        .title("2025 영탁 콘서트 [막창을 구워줘]")
                        .venue("올림픽공원 KSPO DOME")
                        .performanceDate(LocalDateTime.of(2025, 8, 23, 19, 0))
                        .sectionConfigs(createMediumVenueConfig())
                        .build(),

                PerformanceCreateRequest.builder()
                        .title("2025 박효신 콘서트 [LOVERS]")
                        .venue("잠실실내체육관")
                        .performanceDate(LocalDateTime.of(2025, 12, 24, 19, 0))
                        .sectionConfigs(createMediumVenueConfig())
                        .build(),

                // 클래식/오페라 (2개)
                PerformanceCreateRequest.builder()
                        .title("서울시향 2025 신년음악회")
                        .venue("롯데콘서트홀")
                        .performanceDate(LocalDateTime.of(2025, 1, 1, 19, 0))
                        .sectionConfigs(createConcertHallConfig())
                        .build(),

                PerformanceCreateRequest.builder()
                        .title("오페라 〈라 트라비아타〉")
                        .venue("예술의전당 오페라극장")
                        .performanceDate(LocalDateTime.of(2025, 4, 5, 19, 30))
                        .sectionConfigs(createConcertHallConfig())
                        .build(),

                // 페스티벌 (1개)
                PerformanceCreateRequest.builder()
                        .title("2025 펜타포트 록 페스티벌")
                        .venue("인천 송도달빛축제공원")
                        .performanceDate(LocalDateTime.of(2025, 8, 9, 16, 0))
                        .sectionConfigs(createLargeVenueConfig())
                        .build(),

                // 인디/락 (3개)
                PerformanceCreateRequest.builder()
                        .title("2025 혁오 단독콘서트 [Breathe]")
                        .venue("블루스퀘어 마스터카드홀")
                        .performanceDate(LocalDateTime.of(2025, 6, 7, 19, 0))
                        .sectionConfigs(createDefaultConfig())
                        .build(),

                PerformanceCreateRequest.builder()
                        .title("2025 잔나비 전국투어 [JANNABI SMALL THEATER]")
                        .venue("예스24 라이브홀")
                        .performanceDate(LocalDateTime.of(2025, 9, 28, 19, 0))
                        .sectionConfigs(createDefaultConfig())
                        .build(),

                PerformanceCreateRequest.builder()
                        .title("2025 카더가든 콘서트 [Garden Party]")
                        .venue("블루스퀘어 마스터카드홀")
                        .performanceDate(LocalDateTime.of(2025, 11, 16, 19, 0))
                        .sectionConfigs(createDefaultConfig())
                        .build()
        );

        // PerformanceService를 통해 각 공연 생성 (좌석도 함께 생성됨)
        for (PerformanceCreateRequest request : requests) {
            performanceService.createPerformance(request);
        }

        log.info("공연 {}개 생성 완료", requests.size());
    }

    // SectionConfig 생성 메서드들
    private List<SectionConfig> createLargeVenueConfig() {
        return List.of(
                SectionConfig.builder().section(Section.VIP).startRow(1).endRow(10).seatsPerRow(100).price(180_000).build(),
                SectionConfig.builder().section(Section.R).startRow(11).endRow(70).seatsPerRow(100).price(120_000).build(),
                SectionConfig.builder().section(Section.S).startRow(71).endRow(150).seatsPerRow(100).price(80_000).build()
        );
    }

    private List<SectionConfig> createMediumVenueConfig() {
        return List.of(
                SectionConfig.builder().section(Section.VIP).startRow(1).endRow(8).seatsPerRow(100).price(150_000).build(),
                SectionConfig.builder().section(Section.R).startRow(9).endRow(40).seatsPerRow(100).price(100_000).build(),
                SectionConfig.builder().section(Section.S).startRow(41).endRow(80).seatsPerRow(100).price(70_000).build()
        );
    }

    private List<SectionConfig> createTheaterConfig() {
        return List.of(
                SectionConfig.builder().section(Section.VIP).startRow(1).endRow(3).seatsPerRow(100).price(150_000).build(),
                SectionConfig.builder().section(Section.R).startRow(4).endRow(9).seatsPerRow(100).price(100_000).build(),
                SectionConfig.builder().section(Section.S).startRow(10).endRow(15).seatsPerRow(100).price(70_000).build()
        );
    }

    private List<SectionConfig> createConcertHallConfig() {
        return List.of(
                SectionConfig.builder().section(Section.VIP).startRow(1).endRow(5).seatsPerRow(100).price(120_000).build(),
                SectionConfig.builder().section(Section.R).startRow(6).endRow(15).seatsPerRow(100).price(80_000).build(),
                SectionConfig.builder().section(Section.S).startRow(16).endRow(25).seatsPerRow(100).price(50_000).build()
        );
    }

    private List<SectionConfig> createSmallVenueConfig() {
        return List.of(
                SectionConfig.builder().section(Section.VIP).startRow(1).endRow(5).seatsPerRow(100).price(130_000).build(),
                SectionConfig.builder().section(Section.R).startRow(6).endRow(25).seatsPerRow(100).price(90_000).build(),
                SectionConfig.builder().section(Section.S).startRow(26).endRow(50).seatsPerRow(100).price(60_000).build()
        );
    }

    private List<SectionConfig> createDefaultConfig() {
        return List.of(
                SectionConfig.builder().section(Section.VIP).startRow(1).endRow(2).seatsPerRow(100).price(100_000).build(),
                SectionConfig.builder().section(Section.R).startRow(3).endRow(6).seatsPerRow(100).price(70_000).build(),
                SectionConfig.builder().section(Section.S).startRow(7).endRow(10).seatsPerRow(100).price(50_000).build()
        );
    }
}