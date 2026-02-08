package com.channel_chat_service.Entity;

import com.channel_chat_service.Enum.MessageType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessageEntity {
    private long messageId;
    private String content;
    private String sender;
    private MessageType type;
}
