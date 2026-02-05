package com.channel_chat_service.DTO;


import com.channel_chat_service.Enum.MessageType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessage {
    private String content;
    private String sender;
    private MessageType type;
}
