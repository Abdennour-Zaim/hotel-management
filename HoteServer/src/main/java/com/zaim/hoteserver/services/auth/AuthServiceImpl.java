package com.zaim.hoteserver.services.auth;

import com.zaim.hoteserver.dto.SignupRequest;
import com.zaim.hoteserver.dto.UserDto;
import com.zaim.hoteserver.entity.User;
import com.zaim.hoteserver.enums.UserRole;
import com.zaim.hoteserver.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;


    @PostConstruct
    public void createAdminAccount() {
        Optional<User> adminAccount=userRepository.findByUserRole(UserRole.ADMIN);
        if(adminAccount.isEmpty()) {
            User user = new User();
            user.setEmail("admin@gmail.com");
            user.setName("Admin");
            user.setUserRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
            System.out.println("Admin account created successfully");

        }else {
            System.out.println("Admin account already exists");
        }
    }
    public UserDto createUser(SignupRequest signupRequest) {
        if(userRepository.findFirstByEmail(signupRequest.getEmail()).isPresent()) {
            throw new EntityExistsException("Email already exists   ");
        }
        else {
            User user = new User();
            user.setEmail(signupRequest.getEmail());
            user.setName(signupRequest.getName());
            user.setUserRole(UserRole.CUSTOMER);
            user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
            User createdUser=userRepository.save(user);
            return createdUser.getUserDto();
        }
    }
}
