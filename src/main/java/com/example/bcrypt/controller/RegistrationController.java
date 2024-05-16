package com.example.bcrypt.controller;

import com.example.bcrypt.model.Applicants;
import com.example.bcrypt.model.SaveApplicant;
import com.example.bcrypt.repo.ApplicantRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private ApplicantRepository applicantRepository;

    private final SaveApplicant saveApplicant;

    @Autowired
    public RegistrationController(SaveApplicant saveApplicant) {
        this.saveApplicant = saveApplicant;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.initDirectFieldAccess();
        // Trim strings for all String fields
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping("/form")
    public String showRegistrationForm(Model model) {
        model.addAttribute("applicants", new Applicants());
        return "registration";
    }

    @PostMapping("/submitData")
    public String submitRegistration(@ModelAttribute("applicants") @Valid Applicants applicants, BindingResult result) {
        if (result.hasErrors()) {
            return "registration"; // Return back to the form page if there are errors.
        }

        /*
             'rejectValue' method of Validation class of Spring framework.
         */
        if (applicantRepository.existsByUsername(applicants.getUsername())) {
            result.rejectValue("username", "error.applicants", "Username already exists");
            return "registration";
        }

        // Hash password using Bcrypt before saving
        applicants.setPassword(BCrypt.hashpw(applicants.getPassword(), BCrypt.gensalt()));

        saveApplicant.saveApplicant(applicants); // Save the applicant's data into the 'applicants' table.

        /*
              Send the applicant to the retired_page IF the applicant is the age for retirement AND did not
                                 register the occupation as retired.
         */
        if (applicants.getAge() > 67 && !applicants.getOccupation().equals("retired")) { return "retired_page"; }

        return "redirect:/confirmation"; // Redirect to the confirmation page.
    }


    @GetMapping("/confirmation")
    public String confirmationPage() {
        return "registration_confirmation";
    }
}
