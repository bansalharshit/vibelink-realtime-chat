package com.harshit.vibelink.controller;

import com.harshit.vibelink.entity.Message;
import com.harshit.vibelink.entity.Room;
import com.harshit.vibelink.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private final RoomService roomService;

    // create Room
    @PostMapping
public ResponseEntity<?> createRoom(@RequestBody String roomId)
{
         if(roomService.getRoomByRoomId(roomId)!=null)
         {
             // Room is Already Present There
             return ResponseEntity.badRequest().body("Room Already Exist");
         }
         Room room = new Room();
         room.setRoomId(roomId);
         Room savedRoom = roomService.saveRoom(room);
         return ResponseEntity.status(HttpStatus.CREATED).body(room);
}

    // get Room: Join Room
    @GetMapping("/{roomId}")
    public ResponseEntity<?> joinRoom(@PathVariable String roomId)
    {
        Room room = roomService.getRoomByRoomId(roomId);
        if(room==null)
            return ResponseEntity.badRequest().body("Room not Found, Please Join Another Room");
        return ResponseEntity.ok(room);
    }

    // get Messages of Room
    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessagesByRoomId(@PathVariable String roomId,
    @RequestParam(value = "page",defaultValue = "0",required = false) int page, @RequestParam(value = "size",defaultValue = "20",required = false) int size
    ){
        Room room = roomService.getRoomByRoomId(roomId);
        if(room == null)
            return ResponseEntity.badRequest().build();
        List<Message> messages = room.getMessages();
        int start = Math.max(0,messages.size()-(page+1)*size);
        int end = Math.min(messages.size(),start+size);
        List<Message> paginatedMessages= messages.subList(start,end);
        return ResponseEntity.ok(messages);
    }
}

