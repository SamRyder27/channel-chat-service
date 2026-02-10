package com.channel_chat_service.Entity;

import com.channel_chat_service.DTO.SignUpUser;

import com.channel_chat_service.Enum.UserStatusType;
import jakarta.persistence.*;
import lombok.Data;
import org.aspectj.bridge.Message;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Table(name = "ChatUser")
@Data
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true, nullable = false)
    private String email; // by default email is the username for verification
    private String password;
    private String phone;
    @Column (nullable = false)
    private Boolean isActive = true;

    @Column (nullable = false, updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL)
    private Set<GroupMember> groupMemberships = new HashSet<>();

    @OneToMany (mappedBy = "sender", cascade = CascadeType.ALL)
    private Set<Message> sentMessages = new HashSet<>();

    @PrePersist
    protected void  onCreate (){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate (){
        updatedAt = LocalDateTime.now();
    }

    public SignUpUser getSignUpUser() {
        SignUpUser users = new SignUpUser();
        users.setEmail(email);
        users.setName(name);
        users.setPhone(phone);
        users.setPassword(password);

        return users;
    }

}
