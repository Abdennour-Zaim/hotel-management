package com.zaim.hoteserver.services.auth;

import com.zaim.hoteserver.dto.SignupRequest;
import com.zaim.hoteserver.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    UserDto createUser(SignupRequest signupRequest);
}
