package com.harshit.vibelink.repository;

import com.harshit.vibelink.entity.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<Room,String> {

    Room findByRoomId(String roomId);
}
