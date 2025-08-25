package seonjiwon.ticketsync.domain.performance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PerformanceCreateRequest {
    private String title; // 제목
    private String venue; // 공연 장소
    private LocalDateTime performanceDate; // 공연 일자
    private List<SectionConfig> sectionConfigs;
}
