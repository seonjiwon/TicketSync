package seonjiwon.ticketsync.domain.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seonjiwon.ticketsync.domain.reservation.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
