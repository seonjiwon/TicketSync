package seonjiwon.ticketsync.domain.performance.dto;

import lombok.Builder;
import lombok.Getter;
import seonjiwon.ticketsync.domain.performance.entity.Seat;
import seonjiwon.ticketsync.domain.performance.entity.SeatStatus;
import seonjiwon.ticketsync.domain.performance.entity.Section;

import java.util.List;

@Getter
@Builder
public class SeatDto {
    private Section section;
    private int totalSeats;
    private int availableSeats;
    private int price;

    private List<RowDto> rows;
}
