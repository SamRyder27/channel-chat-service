package com.channel_chat_service.Entity;

import com.channel_chat_service.Enum.UserStatusType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table (name = "user_status")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatusType status;

    // Last time user was active
    private LocalDateTime lastSeen;

    // WebSocket session ID (for tracking active connections)
    private String sessionId;

    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        if (status == UserStatusType.ONLINE) {
            lastSeen = LocalDateTime.now();
        }
    }

}
