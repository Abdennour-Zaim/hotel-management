package com.zaim.hoteserver.services.customer.room;

import com.zaim.hoteserver.dto.RoomsResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface RoomService {
    RoomsResponseDto getAvailableRoom(int pageNumber);
}
