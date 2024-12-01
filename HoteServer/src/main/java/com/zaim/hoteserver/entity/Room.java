package com.zaim.hoteserver.entity;

import com.zaim.hoteserver.dto.RoomDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Room {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private Long price;
    private Boolean available;
    public RoomDto getDto() {
        RoomDto dto = new RoomDto();
        dto.setId(id);
        dto.setName(name);
        dto.setType(type);
        dto.setPrice(price);
        dto.setAvailable(available);
        return dto;
    }
}

