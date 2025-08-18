package seonjiwon.ticketsync.domain.performance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seonjiwon.ticketsync.domain.performance.entity.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
