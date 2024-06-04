package com.example.bcrypt.web;

import com.example.bcrypt.model.ChatMessage;
import com.example.bcrypt.model.MessageType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

// Annotates this class as a Spring component, allowing it to be autodetected through classpath scanning
@Component
public class WebsocketEventListener {

    // Logger for logging important events and debugging information
    private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketEventListener.class);

    // Injects the SimpMessageSendingOperations bean, which is used for sending messages to a specific destination
    @Autowired
    private SimpMessageSendingOperations sendingOperations;

    // Event listener method that handles new WebSocket connection events
    @EventListener
    public void handleWebSocketConnectListener(final SessionConnectedEvent event) {
        // Logs the new WebSocket connection event
        LOGGER.info("Received a new web socket connection");
    }

    // Event listener method that handles WebSocket disconnection events
    @EventListener
    public void handleWebSocketDisconnectListener(final SessionDisconnectEvent event) {
        // Retrieves the message headers for the disconnect event
        final StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        // Extracts the username from the session attributes
        final String username = (String) headerAccessor.getSessionAttributes().get("username");

        // If a username is found in the session attributes, logs the disconnection and sends a disconnect message
        if (username != null) {
            LOGGER.info("User Disconnected : " + username);

            // Creates a ChatMessage object representing the disconnect event
            final ChatMessage chatMessage = ChatMessage.builder()
                    .type(MessageType.DISCONNECT)
                    .sender(username)
                    .build();

            // Sends the disconnect message to the "/topic/public" destination
            sendingOperations.convertAndSend("/topic/public", chatMessage);
        }
    }
}
