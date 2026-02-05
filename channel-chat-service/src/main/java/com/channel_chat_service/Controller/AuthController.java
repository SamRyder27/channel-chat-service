package com.channel_chat_service.Controller;


import com.channel_chat_service.DTO.SignUpUser;
import com.channel_chat_service.DTO.User;
import com.channel_chat_service.JwtUtils.JwtUtil;
import com.channel_chat_service.Services.Auth.AuthSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthSerivce authService;

    @PutMapping("/checking")
    public String checkController() {
        return "PUT Controller is working properly";
    }


    @PostMapping("/signup/user")
    public ResponseEntity<?> SignUpUser(@RequestBody SignUpUser signUpUser) {

        User createUser = authService.createUser(signUpUser);
        return new ResponseEntity<>("Saved Succesfully", HttpStatus.OK);
    }
}
