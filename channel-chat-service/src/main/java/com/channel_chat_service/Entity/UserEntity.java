package com.channel_chat_service.Entity;

import com.channel_chat_service.DTO.User;
import jakarta.persistence.*;
import lombok.Data;


@Table(name = "ChatUser")
@Data
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;

    public User getUser() {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPhone(phone);
        user.setPassword(password);

        return user;
    }

}
