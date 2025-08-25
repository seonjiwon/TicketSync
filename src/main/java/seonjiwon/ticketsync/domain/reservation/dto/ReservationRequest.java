package seonjiwon.ticketsync.domain.reservation.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ReservationRequest {
    private Long performanceId;
    private List<Long> seatIds;
}
