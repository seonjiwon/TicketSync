package seonjiwon.ticketsync.domain.performance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seonjiwon.ticketsync.domain.performance.entity.Performance;
import seonjiwon.ticketsync.domain.performance.entity.Seat;
import seonjiwon.ticketsync.domain.performance.entity.SeatStatus;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    int countByPerformance(Performance performance);
    int countByPerformanceAndStatus(Performance performance, SeatStatus status);
}
