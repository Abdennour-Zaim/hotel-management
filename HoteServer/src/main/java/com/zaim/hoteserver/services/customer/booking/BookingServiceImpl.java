package com.zaim.hoteserver.services.customer.booking;

import com.zaim.hoteserver.dto.ReservationDto;
import com.zaim.hoteserver.dto.ReservationResponseDto;
import com.zaim.hoteserver.entity.Reservation;
import com.zaim.hoteserver.entity.Room;
import com.zaim.hoteserver.entity.User;
import com.zaim.hoteserver.enums.ReservationStatus;
import com.zaim.hoteserver.repository.ReservationRepository;
import com.zaim.hoteserver.repository.RoomRepository;
import com.zaim.hoteserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    public static final int SEARCH_RESULT_PER_PAGE=4;
    public boolean postReservation(ReservationDto reservationDto) {
        Optional<User> optionalUser=userRepository.findById(reservationDto.getId());
        Optional<Room> optionalRoom=roomRepository.findById(reservationDto.getRoomId());

        if (optionalRoom.isPresent()&&optionalUser.isPresent()) {
            Reservation reservation=new Reservation();
            reservation.setRoom(optionalRoom.get());
            reservation.setUser(optionalUser.get());
            reservation.setCheckinDate(reservationDto.getCheckinDate());
            reservation.setCheckoutDate(reservationDto.getCheckoutDate());
            reservation.setReservationStatus(ReservationStatus.PENDING);
            reservationRepository.save(reservation);
            Long days= ChronoUnit.DAYS.between(reservation.getCheckinDate(), reservation.getCheckoutDate());
            reservation.setPrice(optionalRoom.get().getPrice()*days);
            return true;
        }
        return false;
    }


    public ReservationResponseDto getAllReservationByUserId(Long userId, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);
        Page<Reservation> reservationPage = reservationRepository.findAllByUserId(userId,pageable);
        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();
        reservationResponseDto.setReservationDtoList(reservationPage.stream().map(Reservation::getReservationDto)
                .collect(Collectors.toList()));
        reservationResponseDto.setPageNumber(reservationPage.getPageable().getPageNumber());
        reservationResponseDto.setTotalPages(reservationPage.getTotalPages());
        return reservationResponseDto;
    }
}
