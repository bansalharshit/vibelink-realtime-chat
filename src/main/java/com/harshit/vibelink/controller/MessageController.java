package com.harshit.vibelink.controller;

import com.harshit.vibelink.entity.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/return-to")
    public Message getContent(@RequestBody Message message)
    {
             try{
                   Thread.sleep(1000); //This is to demonstrate that, after the client sends a message, the server can take as long as it needs to asynchronously process the message. The client can continue with whatever work it needs to do without waiting for the response.
             }catch (InterruptedException e)
             {
                 e.printStackTrace();
             }
             return message;
    }
}
