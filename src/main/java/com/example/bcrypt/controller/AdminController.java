package com.example.bcrypt.controller;

import com.example.bcrypt.CSVExporter;
import com.example.bcrypt.model.Applicants;
import com.example.bcrypt.service.ApplicantService;
import com.example.bcrypt.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private ApplicantService applicantService;

    @Autowired
    private EmailService emailService;

    public AdminController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Trim strings for all String fields
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/about_us")
    public String aboutUs() {
        return "about_us";
    }

    @GetMapping("/manageApplicants")
    public String manageApplicants(Model model) {
        model.addAttribute("applicants", applicantService.findAllApplicants());
        return "manage_applicants";
    }

    /* delete Applicant with the triggering of automated email/acknowledgement of the deletion.
        This setup ensures that when an applicant is deleted, an email is sent to their
             registered email address confirming the deletion of their account. */

    @PostMapping("/deleteApplicant")
    public String deleteUser(@RequestParam("username") String username) {
        // Fetch the applicant's email before deleting
        String email = applicantService.findEmailByUsername(username);

        // Delete the applicant
        applicantService.deleteApplicantByUsername(username);

        // Send the acknowledgement email
        String subject = "Account Deletion Confirmation";
        String text = "Dear Applicant,\n\nYour account has been successfully deleted.\n\nBest regards,\nGovernment support Team";
        emailService.sendEmail(email, subject, text);

        return "success_page";
    }
    //------------------------------ find Applicant by AGE -----------------------------------

    @GetMapping("/findByAgeGet")
    public String findByAgeGet() {
        return "find_by_age_page";
    }

    @GetMapping("/applicantsByAge")
    public String findByAgePost(@RequestParam("age") Integer age, Model model) {
        List<Applicants> applicants = applicantService.findApplicantByAge(age);
        if (applicants.isEmpty()) {
            return "not_found_page";
        }
        model.addAttribute("applicants", applicants);
        return "result_by_age_page";
    }
    //------------------------------ find Applicant by Occupation -----------------------------------

    @GetMapping("/findByOccupationPage")
    public String findByOccupationPage() {
        return "find_by_occupation_page";
    }

    @GetMapping("/findByOccupationResult")
    public String findByOccupation(@RequestParam("occupation") String occupation, Model model) {
        List<Applicants> applicants = applicantService.findApplicantByOccupation(occupation);
        if (applicants.isEmpty()) {
            return "not_found_page";
        }
        model.addAttribute("applicants", applicants);
        return "result_by_occupation_page";
    }
    //------------------------------ find Total number of Applicants -----------------------------------

    @GetMapping("/totalNumberOfApplicants")
    public String totalNumber(Model model) {
        int totalNumberOfApplicants = (applicantService.getTotalNumberOfApplicants()) - 1;
        model.addAttribute("totalNumberOfApplicants", totalNumberOfApplicants);
        return "total_number_page";
    }
    //------------------------------ update the Status of the Application ---------------------------------

    @GetMapping("/updateStatusGetPage")
    public String updateStatusPage(@RequestParam("username") String username, Model model) {
        Applicants applicants = applicantService.findApplicantByUsername(username);
        model.addAttribute("applicants", applicants);
        return "update_status";
    }

        /* Update of the status of the application with the triggering of automated email/acknowledgement.
        This setup ensures that after the status gets amended by the Admin, an email is sent to the Applicant's
             registered email address confirming the update of the status of their application. */

    @GetMapping("/updateStatus")
    public String updateStatus(@ModelAttribute("username") @Valid Applicants applicants, String username, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update_status";
        }
        applicantService.saveApplicant(applicants);

        // Fetch the applicant's email before sending the email
        String email = applicantService.findEmailByUsername(username);

        // Send the acknowledgement email
        String subject = "Status Update Confirmation";
        String text = "Dear Applicant,\n\nThe status of your application has been updated." +
                "\nPlease, visit your account to see the actual status of your application.\n\nBest regards,\nYour Government support Team";
        emailService.sendEmail(email, subject, text);

        return "success_page";
    }

    //---------------------- Exporting the List of the Applicants to CSV file ---------------------------

    @PostMapping("/export")
    public String exportToCSVinProject(Model model) { // saving applicants.CSV file within the project.
        try {
            List<Applicants> applicants = applicantService.findAllApplicants();
            // Define the file path within the project folder.
            String filePath = "applicants.csv";
            File file = new File(filePath);
            FileWriter writer = new FileWriter(file);
            // Export all applicants to CSV file.
            CSVExporter.exportApplicantsToCSV(writer, applicants);
            writer.flush();
            writer.close();
            model.addAttribute("successMessage", "Data exported successfully!");
            return "success_page";
        } catch (IOException e) {
            model.addAttribute("errorMessage", "An error occurred while exporting data to CSV.");
            return "error";
        }
    }
}
