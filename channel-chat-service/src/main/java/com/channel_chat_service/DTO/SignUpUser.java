package com.channel_chat_service.DTO;

import lombok.Data;

@Data
public class SignUpUser {

    private String name;
    private String email;
    private String phone;
    private String password;

}
