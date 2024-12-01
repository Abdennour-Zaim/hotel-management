package com.zaim.hoteserver.services.admins.rooms;
import com.zaim.hoteserver.dto.RoomDto;
import com.zaim.hoteserver.dto.RoomsResponseDto;
import com.zaim.hoteserver.entity.Room;
import com.zaim.hoteserver.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomsServiceImpl implements RoomsService {
    private final RoomRepository roomRepository;

    public Boolean postRoom(RoomDto roomDto) {
        try {
            Room room = new Room();
            room.setName(roomDto.getName());
            room.setPrice(roomDto.getPrice());
            room.setType(roomDto.getType());
            room.setAvailable(roomDto.getAvailable());
            roomRepository.save(room);
            return true;

        }catch (Exception e){return false;}
    }

    public RoomsResponseDto getAllRooms(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 6);
        Page<Room> rooms = roomRepository.findAll(pageable);
        RoomsResponseDto responseDto = new RoomsResponseDto();
        responseDto.setPageNumber(rooms.getPageable().getPageNumber());
        responseDto.setTotalPages(rooms.getTotalPages());
        responseDto.setRoomDtoList(rooms.stream().map(Room::getDto).collect(Collectors.toList()));
        return responseDto;
    }

    public RoomDto getRoomById(Long id) {
        Optional<Room> room = roomRepository.findById(id);
        if (room.isPresent()) {
            return room.get().getDto();
        }else{
            throw new EntityNotFoundException("Room not found");
        }
    }
    public Boolean updateRoom(RoomDto roomDto, Long id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()) {
            Room existingRoom = optionalRoom.get();
            existingRoom.setName(roomDto.getName());
            existingRoom.setPrice(roomDto.getPrice());
            existingRoom.setType(roomDto.getType());
            roomRepository.save(existingRoom);
            return true;

        }
        return false;
    }

    public void deleteRoom(Long id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()) {
            roomRepository.delete(optionalRoom.get());
        } else {
            throw new EntityNotFoundException("Room not found");
        }
    }
}
