package com.harshit.vibelink.service;

import com.harshit.vibelink.entity.Room;
import com.harshit.vibelink.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;

    @Override
    public Room getRoomByRoomId(String roomId) {
        return roomRepository.findByRoomId(roomId);
    }

    @Override
    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }
}
