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

    @GetMapping("/applicant")
    public String applicant() {
        return "applicant";
    }

    @GetMapping("/news")
    public String news() { return "news";}

//----------------------------- Applicant's Deletion request ---------------------------------

    @GetMapping("/applicantsDeletionRequest") // for admin review.
    public String showApplicantsDeletionRequests(Model model) {
        List<Applicants> applicants = applicantService.findApplicantsByDeletion();
        if (applicants.isEmpty()) {
            return "not_found_page";
        }
        model.addAttribute("applicants", applicants);
        return "account_removal_list";
    }

    @GetMapping("/removeAccountRequestPage") // for applicant to send the request.
        public String removeAccountRequestGetPage (@RequestParam("username") String username, Model model) {
        Applicants applicants = applicantService.findApplicantByUsername(username);
        model.addAttribute("applicants", applicants);
        return "account_removal_request";
    }

    @GetMapping("/removeAccountRequestSend")
    public String removeAccountRequestSend(@ModelAttribute("username") @Valid Applicants applicants, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || !applicants.isDeletion()) { // if error or applicant has already sent the request.
            return "error";
        }
        applicantService.saveApplicant(applicants);
        return "success_page";
    }
//------------------------------------- update the Applicant's data ---------------------------------------

    @GetMapping("/updateApplicantGetPage")
    public String updateApplicantPage (@RequestParam("username") String username, Model model) {
        Applicants applicants = applicantService.findApplicantByUsername(username);
        model.addAttribute("applicants", applicants);
        return "update_form";
    }

    @GetMapping("/updateApplicant")
    public String updateApplicant (@ModelAttribute("username") @Valid Applicants applicants, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update_form";
        }
        applicantService.saveApplicant(applicants);
        return "success_page";
    }
}
