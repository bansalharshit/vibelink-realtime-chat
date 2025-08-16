package com.harshit.vibelink.service;

import com.harshit.vibelink.entity.Room;

public interface RoomService {
Room getRoomByRoomId(String roomId);
Room saveRoom(Room room);
}
