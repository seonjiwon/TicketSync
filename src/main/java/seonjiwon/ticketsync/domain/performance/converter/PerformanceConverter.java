package seonjiwon.ticketsync.domain.performance.converter;

import seonjiwon.ticketsync.domain.performance.dto.PerformanceCreateRequest;
import seonjiwon.ticketsync.domain.performance.dto.PerformanceDetailResponse;
import seonjiwon.ticketsync.domain.performance.dto.PerformanceDto;
import seonjiwon.ticketsync.domain.performance.dto.PerformanceListResponse;
import seonjiwon.ticketsync.domain.performance.entity.Performance;

import java.time.LocalDateTime;
import java.util.List;

public class PerformanceConverter {

    public static Performance of(PerformanceCreateRequest request) {
        return Performance.builder()
                .title(request.getTitle())
                .venue(request.getVenue())
                .performanceDate(request.getPerformanceDate())
                .build();
    }

    public static PerformanceDto toPerformanceDto(Performance performance) {
        return PerformanceDto.builder()
                .performanceId(performance.getId())
                .title(performance.getTitle())
                .venue(performance.getVenue())
                .performanceDate(performance.getPerformanceDate())
                .build();
    }

    public static PerformanceListResponse toPerformanceListResponse(List<PerformanceDto> performanceDtos, LocalDateTime nextCursor) {
        return PerformanceListResponse.builder()
                .performanceInfos(performanceDtos)
                .nextCursor(nextCursor)
                .build();
    }

    public static PerformanceDetailResponse toPerformanceDetailResponse(Performance performance, int totalSeatCount, int availableSeatCount) {
        return PerformanceDetailResponse.builder()
                .performanceId(performance.getId())
                .title(performance.getTitle())
                .venue(performance.getVenue())
                .performanceDate(performance.getPerformanceDate())
                .totalSeats(totalSeatCount)
                .availableSeats(availableSeatCount)
                .build();
    }
}
