package com.channel_chat_service.Model;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private  Long id;
    private String name;
    private String email;
    private String phone;

}
