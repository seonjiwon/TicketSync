package seonjiwon.ticketsync.domain.performance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import seonjiwon.ticketsync.common.CustomResponse;
import seonjiwon.ticketsync.domain.performance.dto.PerformanceCreateRequest;
import seonjiwon.ticketsync.domain.performance.dto.PerformanceListResponse;
import seonjiwon.ticketsync.domain.performance.dto.PerformanceDetailResponse;
import seonjiwon.ticketsync.domain.performance.dto.SeatResponse;
import seonjiwon.ticketsync.domain.performance.service.command.PerformanceCommandService;
import seonjiwon.ticketsync.domain.performance.service.query.PerformanceQueryService;
import seonjiwon.ticketsync.domain.performance.service.query.SeatQueryService;

import java.util.Map;

@RestController
@RequestMapping("/performances")
@RequiredArgsConstructor
public class PerformanceController {

    private final PerformanceCommandService performanceCommandService;
    private final PerformanceQueryService performanceQueryService;

    private final SeatQueryService seatQueryService;

    @PostMapping("/create")
    @Operation(method = "POST", summary = "공연 생성", description = "공연을 생성하는 API 입니다.")
    public CustomResponse<?> createPerformance(
            @RequestBody PerformanceCreateRequest performanceCreateRequest

    ) {
        Long performanceId = performanceCommandService.createPerformance(performanceCreateRequest);
        return CustomResponse.onSuccess(Map.of("performanceId", performanceId));
    }

    @GetMapping
    @Operation(method = "GET", summary = "모든 공연 조회", description = "모든 공연을 조회하는 API 입니다.")
    public CustomResponse<PerformanceListResponse> getPerformances(
            @Parameter(description = "페이징을 위한 cursor 입니다.")
            @RequestParam(value = "cursor", required = false) String cursor
    ) {
        PerformanceListResponse performanceListResponse = performanceQueryService.getPerformances(cursor);
        return CustomResponse.onSuccess(performanceListResponse);
    }

    @GetMapping("/{performanceId}")
    @Operation(method = "GET", summary = "공연 세부사항 조회", description = "해당하는 공연의 세부사항을 조회하는 API 입니다.")
    public CustomResponse<PerformanceDetailResponse> getPerformanceSeats(
            @Parameter(description = "공연 Id")
            @PathVariable(value = "performanceId", required = true) Long performanceId
    ) {
        PerformanceDetailResponse performanceDetailResponse = performanceQueryService.getPerformanceDetail(performanceId);

        return CustomResponse.onSuccess(performanceDetailResponse);
    }

    @GetMapping("/{performanceId}/seats")
    @Operation(method = "GET", summary = "공연 좌석 조회", description = "공연의 좌석을 조회하는 API 입니다.")
    public CustomResponse<SeatResponse> getSeats(
            @Parameter(description = "공연 Id")
            @PathVariable(value = "performanceId") Long performanceId
    ) {
        SeatResponse seats = seatQueryService.getSeats(performanceId);
        return CustomResponse.onSuccess(seats);
    }
}
