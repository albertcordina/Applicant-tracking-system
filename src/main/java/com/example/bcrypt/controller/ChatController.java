package com.example.bcrypt.controller;

import com.example.bcrypt.model.Applicants;
import com.example.bcrypt.model.ChatMessage;
import com.example.bcrypt.repo.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/* Annotates this class as a Spring MVC controller, capable of handling web requests */
@Controller
public class ChatController {

    /* Injects the ApplicantRepository bean, which is used for database operations related to applicants */
    @Autowired
    ApplicantRepository applicantRepository;

    /* Handles GET requests to the /chat endpoint */
    @GetMapping("/chat")
    public String chat(Model model) {

        // Gets the currently logged-in user's username
        String username = AccountController.getCurrentLoggedInUsername();  // Method getCurrentLoggedInUsername is in the AccountController class
        // Finds the applicant by username using the applicant repository
        Applicants applicants = applicantRepository.findByUsername(username); // Method in ApplicantRepository to find applicant by username

        // If the applicant is found, adds the applicant's name to the model for the HTML account page
        if (applicants != null) {
            model.addAttribute("name", applicants.getName());  // Add applicant's name to the model for the HTML account page
        }
        // Returns the view name "chat", which corresponds to a chat page
        return "chat";
    }

    /* Handles messages sent to the /chat.send endpoint */
    @MessageMapping("/chat.send")
    /* Broadcasts the message to the /topic/public destination */
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload final ChatMessage chatMessage) {
        // Simply returns the received chat message
        return chatMessage;
    }

    /* Handles messages sent to the /chat.newUser endpoint */
    @MessageMapping("/chat.newUser")
    /* Broadcasts the message to the /topic/public destination */
    @SendTo("/topic/public")
    public ChatMessage newUser(@Payload final ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Adds the new applicant's username to the WebSocket session attributes
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        // Simply returns the received chat message
        return chatMessage;
    }
}
