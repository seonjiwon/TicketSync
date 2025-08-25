package seonjiwon.ticketsync.domain.reservation.service.command.v0;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seonjiwon.ticketsync.common.exception.CustomException;
import seonjiwon.ticketsync.domain.performance.entity.Performance;
import seonjiwon.ticketsync.domain.performance.entity.Seat;
import seonjiwon.ticketsync.domain.performance.exception.PerformanceErrorCode;
import seonjiwon.ticketsync.domain.performance.exception.SeatErrorCode;
import seonjiwon.ticketsync.domain.performance.repository.PerformanceRepository;
import seonjiwon.ticketsync.domain.performance.repository.SeatRepository;
import seonjiwon.ticketsync.domain.reservation.converter.ReservationConverter;
import seonjiwon.ticketsync.domain.reservation.dto.ReservationRequest;
import seonjiwon.ticketsync.domain.reservation.dto.ReservationResponse;
import seonjiwon.ticketsync.domain.reservation.service.command.ReservationCommandService;
import seonjiwon.ticketsync.domain.user.code.UserErrorCode;
import seonjiwon.ticketsync.domain.user.entity.User;
import seonjiwon.ticketsync.domain.user.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class ReservationServiceBasic implements ReservationCommandService {

    private final UserRepository userRepository;
    private final SeatRepository seatRepository;
    private final PerformanceRepository performanceRepository;

    @Override
    public ReservationResponse createReservation(String email, ReservationRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        Performance performance = performanceRepository.findById(request.getPerformanceId())
                .orElseThrow(() -> new CustomException(PerformanceErrorCode.PERFORMANCE_NOT_FOUND));

        List<Seat> seats = request.getSeatIds().stream()
                .map(seatId -> seatRepository.findById(seatId)
                        .orElseThrow(() -> new CustomException(SeatErrorCode.SEAT_NOT_FOUND)))
                .toList();
        return null;
    }
}
