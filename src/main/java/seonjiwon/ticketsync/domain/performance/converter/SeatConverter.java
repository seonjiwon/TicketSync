package seonjiwon.ticketsync.domain.performance.converter;

import seonjiwon.ticketsync.domain.performance.dto.*;
import seonjiwon.ticketsync.domain.performance.entity.Performance;
import seonjiwon.ticketsync.domain.performance.entity.Seat;
import seonjiwon.ticketsync.domain.performance.entity.SeatStatus;
import seonjiwon.ticketsync.domain.performance.entity.Section;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SeatConverter {
    public static Seat of(Performance performance, SectionConfig config, int rowNo,int seatNo) {
        return Seat.builder()
                .performance(performance)
                .section(config.getSection())
                .rowNo(rowNo)
                .seatNo(seatNo)
                .price(config.getPrice())
                .status(SeatStatus.AVAILABLE)
                .build();
    }

    public static SeatResponse toSeatResponse(Performance performance, List<Seat> seats) {
        // Section 별로 그룹화
        List<SeatDto> seatDtos = seats.stream()
                .collect(Collectors.groupingBy(Seat::getSection))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> createSeatDto(entry.getKey(), entry.getValue()))
                .toList();

        // 요약 생성
        SummaryDto summary = createSummary(seats);

        return SeatResponse.builder()
                .performanceId(performance.getId())
                .title(performance.getTitle())
                .venue(performance.getVenue())
                .performanceDate(performance.getPerformanceDate())
                .sections(seatDtos)
                .summary(summary)
                .build();
    }

    private static SeatDto createSeatDto(Section section, List<Seat> sectionSeats) {
        // RowNo로 그룹화
        List<RowDto> rows = sectionSeats.stream()
                .collect(Collectors.groupingBy(Seat::getRowNo))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> createRowDto(entry.getKey(), entry.getValue()))
                .toList();

        return SeatDto.builder()
                .section(section)
                .totalSeats(sectionSeats.size())
                .availableSeats(countByStatus(sectionSeats, SeatStatus.AVAILABLE))
                .price(sectionSeats.get(0).getPrice())
                .rows(rows)
                .build();
    }

    private static RowDto createRowDto(int rowNo, List<Seat> rowSeats) {
        List<SeatDetailDto> seatDetails = rowSeats.stream()
                .sorted(Comparator.comparing(Seat::getSeatNo))
                .map(seat -> SeatDetailDto.builder()
                        .id(seat.getId())
                        .seatNo(seat.getSeatNo())
                        .status(seat.getStatus())
                        .build())
                .toList();

        return RowDto.builder()
                .rowNo(rowNo)
                .seats(seatDetails)
                .build();
    }

    private static SummaryDto createSummary(List<Seat> seats) {
        return SummaryDto.builder()
                .totalSeats(seats.size())
                .availableSeats(countByStatus(seats, SeatStatus.AVAILABLE))
                .reservedSeats(countByStatus(seats, SeatStatus.RESERVED))
                .soldSeats(countByStatus(seats, SeatStatus.SOLD))
                .build();
    }

    private static int countByStatus(List<Seat> seats, SeatStatus status) {
        return (int) seats.stream()
                .filter(seat -> seat.getStatus() == status)
                .count();
    }

}
