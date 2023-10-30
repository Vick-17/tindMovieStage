package com.tindMovie.tindMovie.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.tindMovie.tindMovie.Model.MessageEntity;


@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    private MessageEntity receivePublicMessage(@Payload MessageEntity message) {
        return message;
    }

    @MessageMapping("/private-message")
    public MessageEntity receivePrivateMessage(@Payload MessageEntity message) {
        simpMessagingTemplate.convertAndSendToUser(message.getCanalCode(), "/private", message);
        return message;
    }
}
