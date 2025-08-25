package seonjiwon.ticketsync.domain.reservation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seonjiwon.ticketsync.common.CustomResponse;
import seonjiwon.ticketsync.domain.auth.dto.CustomUserDetails;
import seonjiwon.ticketsync.domain.reservation.service.command.ReservationCommandService;
import seonjiwon.ticketsync.domain.reservation.dto.ReservationRequest;
import seonjiwon.ticketsync.domain.reservation.dto.ReservationResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationCommandService reservationCommandService;

    @PostMapping
    @Operation(method = "POST", summary = "예약 API", description = "공연 예약을 수행하는 API 입니다.")
    public CustomResponse<ReservationResponse> createReservation(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,

            @Parameter(description = "공연 ID + seat ID")
            @RequestBody ReservationRequest request
    ) {
        ReservationResponse response = reservationCommandService.createReservation(customUserDetails.getUsername(), request);

        return CustomResponse.onSuccess(response);
    }
}
