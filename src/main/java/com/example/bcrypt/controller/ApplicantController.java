package com.example.bcrypt.controller;

import com.example.bcrypt.model.Applicants;
import com.example.bcrypt.service.ApplicantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ApplicantController {

    @Autowired // field injection
    private ApplicantService applicantService;

    @GetMapping("/")
    public String home() {
        return "main_page";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/login")
    public String login() {
        return "login_page";
    }

    @GetMapping("/news")
    public String news() {
        return "news";
    }

//----------------------------- Applicant's Deletion request ---------------------------------

    @GetMapping("/removeAccountRequestPage") // for applicant to send the request.
    public String removeAccountRequestGetPage(@RequestParam("username") String username, Model model) {
        Applicants applicants = applicantService.findApplicantByUsername(username);
        model.addAttribute("applicants", applicants);
        return "account_removal_request";
    }

    @GetMapping("/removeAccountRequestSend") // sending the removal of the account request to admin.
    public String removeAccountRequestSend(@ModelAttribute("username") @Valid Applicants applicants, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { return "error"; }// if error .
        applicantService.saveApplicant(applicants);
        return "success_page";
    }

    @GetMapping("/applicantsDeletionRequest") // for admin review.
    public String showApplicantsDeletionRequests(Model model) {
        List<Applicants> applicants = applicantService.findApplicantsByDeletion();
        if (applicants.isEmpty()) { // applicants.isEmpty() is to check if a LIST of applicants is empty.
            return "not_found_page";
        }
        model.addAttribute("applicants", applicants);
        return "account_removal_list";
    }
    
    @PostMapping("/removeAccountDeletionRequest") // removal of the account deletion request managed by the admin.
    public String removeAccountDeletionRequest(@RequestParam("username") String username, Model model) {
        Applicants applicant = applicantService.findApplicantByUsername(username);
        // applicant == null is to check if a SINGLE applicant object is null.
        if (applicant == null) {return "not_found_page"; } // Handle the case where the applicant is not found.
        applicant.setDeletion(false); // Set to 'false' the 'deletion' boolean.
        applicantService.saveApplicant(applicant);
        model.addAttribute("applicants", applicantService.findAllApplicants()); // Update the model with all applicants

        return "success_page";
    }

//------------------------------------- update the Applicant's data ---------------------------------------

    @GetMapping("/updateApplicantGetPage")
    public String updateApplicantPage(@RequestParam("username") String username, Model model) {
        Applicants applicants = applicantService.findApplicantByUsername(username);
        model.addAttribute("applicants", applicants);
        return "update_form";
    }

    @GetMapping("/updateApplicant")
    public String updateApplicant(@ModelAttribute("username") @Valid Applicants applicants, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update_form";
        }
        applicantService.saveApplicant(applicants);
        return "success_page";
    }
}
