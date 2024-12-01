package com.zaim.hoteserver.services.admins.rooms;

import com.zaim.hoteserver.dto.RoomDto;
import com.zaim.hoteserver.dto.RoomsResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface RoomsService {
    Boolean postRoom(RoomDto roomDto);
    RoomsResponseDto getAllRooms(int pageNumber);
    RoomDto getRoomById(Long id);
    Boolean updateRoom(RoomDto roomDto, Long id);
    void deleteRoom(Long id);
}


