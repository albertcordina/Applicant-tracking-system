package com.example.bcrypt.model;

import lombok.Builder;
import lombok.Getter;

@Builder
public class ChatMessage {

    @Getter
    private MessageType type;

    @Getter
    private String message;

    @Getter
    private String sender;
}
