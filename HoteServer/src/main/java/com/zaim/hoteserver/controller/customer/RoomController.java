package com.zaim.hoteserver.controller.customer;

import com.zaim.hoteserver.services.customer.room.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class RoomController {
    private final RoomService roomService;
    public ResponseEntity<?> getAvailableRooms(@PathVariable int pageNumber) {
        return ResponseEntity.ok(roomService.getAvailableRoom(pageNumber));
    }
}
