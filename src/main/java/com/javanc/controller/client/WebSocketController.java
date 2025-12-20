package com.javanc.controller.client;


import com.javanc.service.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class WebSocketController {

    NotificationService notificationService;

    @MessageMapping("/notification/read-all")
    @SendToUser("/queue/notification/ack")
    public String markAllAsRead(@Payload Map<String, Object> payload) {
        Integer userIdInt = (Integer) payload.get("userId");
        Long userId = userIdInt.longValue();
        notificationService.updateStatus(userId);
        return "success";
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable ex) {
        return ex.getMessage();
    }
}
