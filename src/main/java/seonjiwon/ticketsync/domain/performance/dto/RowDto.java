package seonjiwon.ticketsync.domain.performance.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RowDto {
    private int rowNo;
    private List<SeatDetailDto> seats;
}
