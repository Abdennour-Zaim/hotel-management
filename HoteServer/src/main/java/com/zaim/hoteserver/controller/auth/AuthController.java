package com.zaim.hoteserver.controller.auth;

import com.zaim.hoteserver.dto.AuthenticationRequest;
import com.zaim.hoteserver.dto.AuthenticationResponse;
import com.zaim.hoteserver.dto.SignupRequest;
import com.zaim.hoteserver.dto.UserDto;
import com.zaim.hoteserver.entity.User;
import com.zaim.hoteserver.repository.UserRepository;
import com.zaim.hoteserver.services.auth.AuthService;
import com.zaim.hoteserver.services.jwt.UserService;
import com.zaim.hoteserver.utill.JwtUtil;
import io.jsonwebtoken.io.Decoders;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;




@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository UserRepository;
    private final JwtUtil JwtUtil;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestBody SignupRequest signupRequest) {
        try {
            UserDto createdUser = authService.createUser(signupRequest);
            return new ResponseEntity<>(createdUser, HttpStatus.OK);
        }catch (EntityExistsException entityExistsException){
            return new ResponseEntity<>("User Already Exists", HttpStatus.NOT_ACCEPTABLE);
        }catch (Exception e){
            return new ResponseEntity<>("User not created! come again later", HttpStatus.BAD_REQUEST);
        }

    }
    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationResponse(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));
        }catch (BadCredentialsException e){
            logger.info("tra chi mouchkil");
            throw new BadCredentialsException("Invalid email or password");

        }finally {
            logger.info("hada huwa l email : "+authenticationRequest.getEmail());
            logger.info("hada huwa l password : "+authenticationRequest.getPassword());
        }


        final UserDetails userDetails=userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> user=userRepository.findFirstByEmail(userDetails.getUsername());



            final String jwt=jwtUtil.generateToken(userDetails);


        AuthenticationResponse authenticationResponse=new AuthenticationResponse();
        if (user.isPresent()){
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserRole(user.get().getUserRole());
            authenticationResponse.setUserId(user.get().getId());
        }


        return authenticationResponse;
    }
}
