package seonjiwon.ticketsync.domain.performance.dto;

import lombok.Builder;
import lombok.Getter;
import seonjiwon.ticketsync.domain.performance.entity.SeatStatus;

@Getter
@Builder
public class SeatDetailDto {
    private Long id;
    private int seatNo;
    private SeatStatus status;
}
