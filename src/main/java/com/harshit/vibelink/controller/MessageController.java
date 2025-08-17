package com.harshit.vibelink.controller;

import com.harshit.vibelink.entity.Message;
import com.harshit.vibelink.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    // Client sends to /app/chat.sendMessage/{roomId}
    @MessageMapping("/chat.sendMessage/{roomId}")
    @SendTo("/topic/{roomId}")
    public Message handleSend(@DestinationVariable String roomId, @Payload Message incoming) {
        // Persist then broadcast the saved copy (contains id & timestamp)
        return messageService.persist(roomId, incoming.getSender(), incoming.getContent());
    }
}
