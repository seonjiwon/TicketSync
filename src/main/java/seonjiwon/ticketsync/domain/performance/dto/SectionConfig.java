package seonjiwon.ticketsync.domain.performance.dto;

import lombok.Builder;
import lombok.Data;
import seonjiwon.ticketsync.domain.performance.entity.Section;

@Data
@Builder
public class SectionConfig {
    private Section section;
    private int startRow;
    private int endRow;
    private int seatsPerRow;
    private int price;
}