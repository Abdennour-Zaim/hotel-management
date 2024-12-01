package com.zaim.hoteserver.services.customer.booking;

import com.zaim.hoteserver.dto.ReservationDto;
import com.zaim.hoteserver.dto.ReservationResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface BookingService {
    boolean postReservation(ReservationDto reservationDto);

    ReservationResponseDto getAllReservationByUserId(Long userId, int pageNumber);
}
