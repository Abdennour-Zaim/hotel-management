package com.zaim.hoteserver.dto;

import com.zaim.hoteserver.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private UserRole role;

}
