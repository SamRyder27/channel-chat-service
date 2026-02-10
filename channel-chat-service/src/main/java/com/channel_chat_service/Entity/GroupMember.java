package com.channel_chat_service.Entity;


import com.channel_chat_service.Enum.GroupRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table (name = "group_members", uniqueConstraints =
        {@UniqueConstraint(columnNames = {"chat_room_id", "user_id"})},
        indexes = {@Index(name = "idx_user_chatroom", columnList = "user_id, chat_room_id")})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false)
    private GroupRole role;

    @Column (nullable = false, updatable = false)
    private LocalDateTime jointedAt;

    // private Boolean notificationEnabled;
    // rpivate LocalDateTime lastReadAt;

    protected  void  onCreate(){
        jointedAt = LocalDateTime.now();
    }

    public boolean isAdmin (){
        return  role == GroupRole.ADMIN;
    }

}
