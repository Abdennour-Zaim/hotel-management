package com.zaim.hoteserver.services.customer.room;

import com.zaim.hoteserver.dto.RoomsResponseDto;
import com.zaim.hoteserver.entity.Room;
import com.zaim.hoteserver.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    public RoomsResponseDto getAvailableRoom(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 6);
        Page<Room> rooms = roomRepository.findByAvailable(true,pageable);
        RoomsResponseDto responseDto = new RoomsResponseDto();
        responseDto.setPageNumber(rooms.getPageable().getPageNumber());
        responseDto.setTotalPages(rooms.getTotalPages());
        responseDto.setRoomDtoList(rooms.stream().map(Room::getDto).collect(Collectors.toList()));
        return responseDto;
    }
}
