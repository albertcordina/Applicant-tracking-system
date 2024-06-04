package com.example.bcrypt.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// Annotates this class as a configuration class that contains @Bean definitions
@Configuration
// Enables WebSocket message handling, backed by a message broker
@EnableWebSocketMessageBroker
public class WebSocketMessageConfig implements WebSocketMessageBrokerConfigurer {

    // Registers STOMP (Simple Text Oriented Messaging Protocol) endpoints and maps each to a specific URL
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Adds an endpoint that clients will use to connect to the WebSocket server with SockJS fallback options
        registry.addEndpoint("/chat").withSockJS();
    }

    // Configures the message broker options
    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        // Sets the application destination prefix for messages bound for @MessageMapping-annotated methods
        registry.setApplicationDestinationPrefixes("/app");
        // Enables a simple in-memory message broker and sets the destination prefix for it
        registry.enableSimpleBroker("/topic");
    }
}
