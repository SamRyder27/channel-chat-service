package com.channel_chat_service.Services.Auth;

import com.channel_chat_service.DTO.SignUpUser;


public interface AuthSerivce {

    public SignUpUser createUser(SignUpUser signUpUser);

    public Boolean presentByEmail(String email);
}
