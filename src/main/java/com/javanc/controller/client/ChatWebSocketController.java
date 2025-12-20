package com.javanc.controller.client;

import com.javanc.model.request.client.ChatMessageRequest;
import com.javanc.model.response.client.ChatMessageResponse;
import com.javanc.service.ChatMessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ChatWebSocketController {

    SimpMessagingTemplate messagingTemplate;
    ChatMessageService chatMessageService;

    @MessageMapping("/room/{roomId}")
    public void receiveAndSendMessage(@DestinationVariable Long roomId, @Payload ChatMessageRequest message) {
        ChatMessageResponse mes = chatMessageService.createChatMessage(roomId, message);
        messagingTemplate.convertAndSend("/topic/room/" + roomId, mes);
    }
}
