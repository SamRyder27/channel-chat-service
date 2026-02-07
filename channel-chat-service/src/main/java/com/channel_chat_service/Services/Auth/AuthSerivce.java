package com.channel_chat_service.Services.Auth;

import com.channel_chat_service.DTO.SignUpUser;
import com.channel_chat_service.DTO.Users;

public interface AuthSerivce {

    public Users createUser(SignUpUser signUpUser);

    public Boolean presentByEmail(String email);
}
