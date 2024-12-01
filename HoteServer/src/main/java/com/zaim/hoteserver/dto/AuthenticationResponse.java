package com.zaim.hoteserver.dto;

import com.zaim.hoteserver.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwt;
    private Long userId;
    private UserRole userRole;

}
