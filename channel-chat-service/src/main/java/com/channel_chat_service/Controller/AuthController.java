package com.channel_chat_service.Controller;


import com.channel_chat_service.DTO.AuthRequest;
import com.channel_chat_service.DTO.SignUpUser;

import com.channel_chat_service.JwtUtils.JwtUtil;
import com.channel_chat_service.Services.Auth.AuthSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;


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

        if (authService.presentByEmail((signUpUser.getEmail()))) {
            return new ResponseEntity<>("User already exists, please use a different email", HttpStatus.NOT_ACCEPTABLE);
        }
        SignUpUser createUsers = authService.createUser(signUpUser);
        return new ResponseEntity<>("Saved Succesfully", HttpStatus.OK);
    }

    @PostMapping("/signin/user")
    public ResponseEntity<?> signInUser(@RequestBody AuthRequest authRequest) throws IOException {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (authRequest.getUsername(), authRequest.getPassword()));
        } catch (AuthenticationException e) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("message", "Bad Credential");
            map.put("status", "FALSE");
            return new ResponseEntity<Object>(map, HttpStatus.UNAUTHORIZED);
        }

        //SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtil.generateTokenFromUsername(userDetails);
        //AuthResponse authResponse =  new AuthResponse(jwtToken, userDetails.getUsername());
        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }

}
