package com.harshit.vibelink.repository;

import com.harshit.vibelink.entity.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByRoomIdOrderByLocalDateTimeAsc(String roomId);
    List<Message> findByRoomIdOrderByLocalDateTimeDesc(String roomId, Pageable pageable);
}
