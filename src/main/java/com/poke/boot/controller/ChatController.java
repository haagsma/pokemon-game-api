package com.poke.boot.controller;

import com.poke.boot.dto.ChatGlobalDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/global")
    @SendTo("/chat/global")
    public ChatGlobalDTO getChatGlobal(ChatGlobalDTO chatGlobalDTO) {
        return chatGlobalDTO;
    }

}
