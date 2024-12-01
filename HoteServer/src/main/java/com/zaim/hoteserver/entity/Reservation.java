package com.zaim.hoteserver.entity;

import com.zaim.hoteserver.dto.ReservationDto;
import com.zaim.hoteserver.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Data
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private Long price;
    private ReservationStatus reservationStatus;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Room room;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public ReservationDto getReservationDto() {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(id);
        reservationDto.setPrice(price);
        reservationDto.setCheckinDate(checkinDate);
        reservationDto.setCheckoutDate(checkoutDate);
        reservationDto.setReservationStatus(reservationStatus);
        reservationDto.setUserId(user.getId());
        reservationDto.setUserName(user.getName());
        reservationDto.setRoomId(room.getId());
        reservationDto.setRoomName(room.getName());
        reservationDto.setRoomType(room.getType());
        return reservationDto;
    }
}
