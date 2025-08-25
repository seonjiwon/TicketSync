package seonjiwon.ticketsync.domain.performance.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class SeatResponse {
    private Long performanceId;
    private String title;
    private String venue;
    private LocalDateTime performanceDate;

    private List<SeatDto> sections;
    private SummaryDto summary;
}
