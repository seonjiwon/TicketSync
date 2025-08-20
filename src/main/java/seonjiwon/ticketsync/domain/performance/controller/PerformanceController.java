package seonjiwon.ticketsync.domain.performance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import seonjiwon.ticketsync.common.CustomResponse;
import seonjiwon.ticketsync.domain.performance.dto.PerformanceCreateRequest;
import seonjiwon.ticketsync.domain.performance.dto.PerformanceListResponse;
import seonjiwon.ticketsync.domain.performance.dto.PerformanceDetailResponse;
import seonjiwon.ticketsync.domain.performance.service.PerformanceService;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/performances")
@RequiredArgsConstructor
public class PerformanceController {
    private final PerformanceService performanceService;

    @GetMapping
    public CustomResponse<PerformanceListResponse> getPerformances(
            @Parameter(description = "페이징을 위한 cursor 입니다.")
            @RequestParam(value = "cursor", required = false) String cursor
    ) {
        PerformanceListResponse performanceListResponse = performanceService.getPerformances(cursor);
        return CustomResponse.onSuccess(performanceListResponse);
    }

    @GetMapping("/{performanceCode}")
    @Operation(description = "공연 조회를 위한 API")
    public CustomResponse<PerformanceDetailResponse> getPerformanceSeats(
            @Parameter(description = "공연 Id")
            @PathVariable(value = "performanceCode", required = true) String performanceCode
    ) {
        PerformanceDetailResponse performanceDetailResponse = performanceService.getPerformanceDetail(performanceCode);

        return CustomResponse.onSuccess(performanceDetailResponse);
    }

    @PostMapping("/create")
    public CustomResponse<?> createPerformance(
            @RequestBody PerformanceCreateRequest performanceCreateRequest

    ) {
        String performanceCode = performanceService.createPerformance(performanceCreateRequest);
        return CustomResponse.onSuccess(Map.of("performanceCode", performanceCode));
    }
}
