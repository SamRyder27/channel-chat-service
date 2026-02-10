package com.channel_chat_service.DTO;


import com.channel_chat_service.Enum.ChatRoomType;
import com.channel_chat_service.Enum.MessageType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @NotNull (message = "Room Id is required for the group chat")
    private Long chatRoomId;

    @NotNull (message = "Type is required for messaging")
    private MessageType messagetype;

    private String content;

    //must be populated from the server end not the user end
    private Long senderId;
    private Long senderUsername;

}
