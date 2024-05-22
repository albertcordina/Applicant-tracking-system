package com.example.bcrypt.controller;

import com.example.bcrypt.model.Applicants;
import com.example.bcrypt.repo.ApplicantRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AccountController {
/*
    Print out some data for the applicant without modifying anything on the server, a GET request is suitable.
    Send data to the server for processing and then display a response to the user, a POST request is appropriate.

    GET or POST requests connect with HTML page by their endpoint (URL path) associated with the request.
    The return statement within the controller method specifies the response to be sent back to the client,
          which may include rendering an HTML page associated with the request.
 */

    @Autowired
    private ApplicantRepository applicantRepository;

    @GetMapping("/account")
    public String showAccountPage(Model model) {

        // gets the currently logged-in user's username.
        String username = getCurrentLoggedInUsername();  // method to get the current username.
        Applicants applicants = applicantRepository.findByUsername(username); // method in ApplicantRepository to find applicant by username.

        if (applicants != null) {
            model.addAttribute("name", applicants.getName());  // Add applicant's name to the model for the HTML account page.
        }
        return "account";  // Return the account.html template
    }

    /*
         The method shows the data of the Applicant (accessed from the account of the Applicant).
     */
    @GetMapping("/getAccountDetails")
    public String showManageAccountPage(Model model) {

        // gets the currently logged-in user's username.
        String username = getCurrentLoggedInUsername(); // we call the method below.
        Applicants applicants = applicantRepository.findByUsername(username);

        if (applicants != null) {
            model.addAttribute("username", applicants.getUsername());
            model.addAttribute("name", applicants.getName());
            model.addAttribute("surname", applicants.getSurname());
            model.addAttribute("age", applicants.getAge());
            model.addAttribute("occupation", applicants.getOccupation());
            model.addAttribute("income", applicants.getIncome());
            model.addAttribute("email", applicants.getEmail());
            model.addAttribute("comments", applicants.getComments());
            model.addAttribute("status", applicants.getStatus());
        }
        return "manage_account";
    }

    /*
          By using getCurrentLoggedInUsername across our application, we ensure consistency
            and reusability in retrieving the username of the currently authenticated user,
                   without duplicating the authentication logic in multiple places.
     */
    public String getCurrentLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            } else {
                return principal.toString();
            }
        }
        return null;  // No authenticated applicant.
    }
}
