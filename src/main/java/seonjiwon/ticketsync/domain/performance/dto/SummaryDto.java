package seonjiwon.ticketsync.domain.performance.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SummaryDto {
    private int totalSeats;
    private int availableSeats;
    private int reservedSeats;
    private int soldSeats;
}
