package com.channel_chat_service.Entity;

import com.channel_chat_service.DTO.Users;
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

    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String phone;

    public Users getUser() {
        Users users = new Users();
        users.setEmail(email);
        users.setName(name);
        users.setPhone(phone);
        users.setPassword(password);

        return users;
    }

}
