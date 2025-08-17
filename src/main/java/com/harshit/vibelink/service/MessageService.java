package com.harshit.vibelink.service;

import com.harshit.vibelink.entity.Message;
import com.harshit.vibelink.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public Message persist(String roomId, String sender, String content) {
        Message m = new Message();
        m.setRoomId(roomId);
        m.setSender(sender);
        m.setContent(content);
        m.setLocalDateTime(LocalDateTime.now());
        return messageRepository.save(m);
    }
}
