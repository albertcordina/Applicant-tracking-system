package com.example.bcrypt.controller;

import com.example.bcrypt.model.Applicants;
import com.example.bcrypt.repo.ApplicantRepository;
import com.example.bcrypt.service.EmailService;
import jakarta.servlet.http.HttpSession;
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

import java.security.SecureRandom;
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

    @Autowired
    private EmailService emailService;

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

    /* The method shows the data of the Applicant (accessed from the account of the Applicant) */
    @GetMapping("/getAccountDetails")
    public String showManageAccountPage(HttpSession session, Model model) {
        if (session.getAttribute("is2faAuthenticated") == null || !(Boolean) session.getAttribute("is2faAuthenticated")) {
            return "redirect:/2fa";
        }

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

    /* Displays the 2FA page and sends a 2FA code to the user's email */
    @GetMapping("/2fa")
    public String show2faPage(HttpSession session) {
        // Retrieves the username of the currently logged-in user
        String username = getCurrentLoggedInUsername();
        // Retrieves the email associated with the username
        String email = applicantRepository.findEmailByUsername(username);
        // Generates a 2FA code
        String code = generate2faCode();

        // Sets the 2FA code in the session attribute
        session.setAttribute("2faCode", code);
        // Constructs the email subject
        String emailSubject = "Your 2FA Code";
        // Constructs the email body
        String emailBody = "Dear Applicant,\n\n" +
                "Thank you for using our application. Here is your 2FA code:\n\n" +
                "Code: " + code + "\n\n" +
                "Please use this code to complete the authentication process.\n\n" +
                "If you did not request this code, please ignore this email.\n\n" +
                "Best regards,\n" +
                "Your Government support Team";

        // Sends the email with the 2FA code
        emailService.sendEmail(email, emailSubject, emailBody);
        // Returns the 2FA page
        return "2fa";
    }

    /* Verifies the 2FA code submitted by the user */
    @PostMapping("/verify-2fa")
    public String verify2fa(@RequestParam("code") String code, HttpSession session) {
        // Retrieves the 2FA code from the session
        String sessionCode = (String) session.getAttribute("2faCode");

        // Compares the submitted code with the session code
        if (code.equals(sessionCode)) {
            // If the codes match, sets the session attribute to indicate successful 2FA authentication
            session.setAttribute("is2faAuthenticated", true);
            // Redirects to the account details page
            return "redirect:/getAccountDetails";
        } else {
            // If the codes do not match, redirects to the 2FA page with an error parameter
            return "redirect:/2fa?error";
        }
    }

    /* Generates a random 6-digit 2FA code */
    private String generate2faCode() {
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(1000000);
        return String.format("%06d", num);
    }


    /*
          By using getCurrentLoggedInUsername across our application, we ensure consistency
            and reusability in retrieving the username of the currently authenticated user,
                   without duplicating the authentication logic in multiple places.
     */
    public static String getCurrentLoggedInUsername() {
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
