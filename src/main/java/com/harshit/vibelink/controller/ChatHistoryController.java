package com.harshit.vibelink.controller;

import com.harshit.vibelink.entity.Message;
import com.harshit.vibelink.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class ChatHistoryController {

    private final MessageRepository messageRepository;

    // Fetch last N messages (default 50) oldest-first
    @GetMapping("/{roomId}/messages")
    public List<Message> getRoomMessages(@PathVariable String roomId,
                                         @RequestParam(defaultValue = "50") int last) {
        if (last <= 0) return Collections.emptyList();
        var page = PageRequest.of(0, last);
        var newestFirst = messageRepository.findByRoomIdOrderByLocalDateTimeDesc(roomId, page);
        Collections.reverse(newestFirst);
        return newestFirst;
    }
}
