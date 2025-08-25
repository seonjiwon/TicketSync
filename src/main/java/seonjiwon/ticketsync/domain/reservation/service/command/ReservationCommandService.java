package seonjiwon.ticketsync.domain.reservation.service.command;

import seonjiwon.ticketsync.domain.reservation.dto.ReservationRequest;
import seonjiwon.ticketsync.domain.reservation.dto.ReservationResponse;

public interface ReservationCommandService {
    ReservationResponse createReservation(String email, ReservationRequest request);
}
