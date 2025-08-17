package com.harshit.vibelink.service;

import com.harshit.vibelink.entity.Room;

import java.util.List;

public interface RoomService {
Room getRoomByRoomId(String roomId);
Room saveRoom(Room room);
List<Room> getAllRooms();
}
