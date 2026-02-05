package com.channel_chat_service.Services.Auth;

import com.channel_chat_service.DTO.SignUpUser;
import com.channel_chat_service.DTO.User;

public interface AuthSerivce {

    public User createUser(SignUpUser signUpUser);

    public Boolean presentByEmail(String email);
}
