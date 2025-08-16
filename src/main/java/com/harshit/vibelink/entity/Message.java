package com.harshit.vibelink.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String sender;
    private String content;
    private LocalDateTime localDateTime;

    public Message(String sender,String content)
    {
        this.sender = sender;
        this.content = content;
        this.localDateTime = LocalDateTime.now();
    }
}
