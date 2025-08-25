package seonjiwon.ticketsync.domain.performance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seonjiwon.ticketsync.domain.performance.entity.Performance;
import seonjiwon.ticketsync.domain.performance.entity.Seat;
import seonjiwon.ticketsync.domain.performance.entity.SeatStatus;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findAllByPerformance(Performance performance);

    int countByPerformance(Performance performance);
    int countByPerformanceAndStatus(Performance performance, SeatStatus status);
}
