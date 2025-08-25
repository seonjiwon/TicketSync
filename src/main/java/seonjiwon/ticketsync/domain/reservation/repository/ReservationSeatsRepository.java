package seonjiwon.ticketsync.domain.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seonjiwon.ticketsync.domain.reservation.entity.ReservationSeat;

public interface ReservationSeatsRepository extends JpaRepository<ReservationSeat, Long> {
}
