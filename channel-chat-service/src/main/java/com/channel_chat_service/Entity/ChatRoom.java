package com.channel_chat_service.Entity;

import com.channel_chat_service.Enum.ChatRoomType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "chat_rooms")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long Id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChatRoomType roomType;

    private String name; // this group name can be auto generated

    private  String description;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "created_by")
    private UserEntity createdBy;

    @Column (nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany (mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy ("createdAt ASC")
    private Set<Message> messages = new HashSet<>();

    @OneToMany (mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GroupMember> members  = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Helper method to check if a user is a member
    public boolean isMember(Long userId) {
        return members.stream()
                .anyMatch(member -> member.getUser().getId().equals(userId));
    }

    public int getMemberCount() {
        return members.size();
    }


}
