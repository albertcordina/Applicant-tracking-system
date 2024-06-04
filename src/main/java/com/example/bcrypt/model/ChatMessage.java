package com.example.bcrypt.model;

import lombok.Builder;
import lombok.Getter;

/* 
      By eliminating boilerplate code, Spring Boot Lombok enhances
      productivity, readability, and maintainability of projects.

 @Builder design pattern allows for a flexible and readable way to construct instances of the class.
   The @Builder annotation automatically generates a static inner builder class with methods for 
       setting the fields and a build() method to create an instance of the ChatMessage class.  
*/

@Builder
public class ChatMessage {

    // Generates a getter method for the 'type' field
    @Getter
    private MessageType type;

    // for the 'message' field
    @Getter
    private String message;

    // for the 'sender' field
    @Getter
    private String sender;
}
