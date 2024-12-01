package com.zaim.hoteserver.services.admins.reservation;

import com.zaim.hoteserver.dto.ReservationResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface ReservationService {
    ReservationResponseDto getAllReservations(int pageNumber);
    boolean changeReservationStatus(Long id,String status);
}
