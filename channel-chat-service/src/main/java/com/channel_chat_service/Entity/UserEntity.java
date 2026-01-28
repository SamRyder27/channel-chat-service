package com.channel_chat_service.Entity;

import jakarta.persistence.*;
import lombok.Data;


@Table (name = "ChatUser")
@Data
@Entity
public class UserEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private  Long id;
    private String name;
    private String email;
    private String phone;
}
