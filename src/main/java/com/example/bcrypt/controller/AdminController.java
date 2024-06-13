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
    public String manageApplicants(Model model) { // find All and Total number of Applicants.

        int totalNumberOfApplicants = (applicantService.getTotalNumberOfApplicants()) - 1;
        model.addAttribute("totalNumberOfApplicants", totalNumberOfApplicants);

        model.addAttribute("applicants", applicantService.findAllApplicants());
        return "manage_applicants";
    }

    /* delete Applicant with the triggering of automated email/acknowledgement of the deletion.
        This setup ensures that when an applicant is deleted, an email is sent to their
             registered email address confirming the deletion of their account. */

    @PostMapping("/deleteApplicant")
    public String deleteApplicantAcknowledgement(@RequestParam("username") String username) {
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

    @GetMapping("/applicantsByAge")
    public String findByAgeResult(@RequestParam(value = "age", required = false) Integer age,
                                  /* 'required = false' attribute is used in the @RequestParam annotation to indicate that the parameters are optional.
                                             This means that the user can provide these parameters, but they are not required to. */
                                  @RequestParam(value = "minAge", required = false) Integer minAge,
                                  @RequestParam(value = "maxAge", required = false) Integer maxAge,
                                  Model model) {
        List<Applicants> applicants;

        if (age != null) {
            applicants = applicantService.findApplicantByAge(age);
            model.addAttribute("age", age); // Add AGE to model.

        } else if (minAge != null && maxAge != null) {
            applicants = applicantService.findApplicantsByAgeRange(minAge, maxAge);
            model.addAttribute("minAge", minAge);
            model.addAttribute("maxAge", maxAge);
        } else {
            return "not_found_page";
        }

        if (applicants.isEmpty()) {
            return "not_found_page";
        }

        model.addAttribute("applicants", applicants);
        return "result_by_age_page";
    }
    //------------------------------ find Applicant by Income -----------------------------------

    @GetMapping("/applicantsByIncome")
    public String findByIncomeResult(@RequestParam(value = "income", required = false) Double income,
                                     /* 'required = false' attribute is used in the @RequestParam annotation to indicate that the parameters are optional.
                                                              This means that the user can provide these parameters, but they are not required to. */
                                     @RequestParam(value = "minIncome", required = false) Double minIncome,
                                     @RequestParam(value = "maxIncome", required = false) Double maxIncome,
                                     Model model) {
        List<Applicants> applicants;

        if (income != null) {
            applicants = applicantService.findApplicantsByIncome(income);
            model.addAttribute("income", income); // Add income to model.
        } else if (minIncome != null && maxIncome != null) {
            applicants = applicantService.findApplicantsByIncomeRange(minIncome, maxIncome);
            model.addAttribute("minIncome", minIncome); // Add minIncome to model.
            model.addAttribute("maxIncome", maxIncome); // Add maxIncome to model.
        } else {
            return "not_found_page";
        }
        if (applicants.isEmpty()) {
            return "not_found_page";
        }

        model.addAttribute("applicants", applicants);
        return "result_by_income_page";
    }

    //------------------------------ find Applicant by Occupation -----------------------------------

    @GetMapping("/findByOccupationResult")
    public String findByOccupationResult(@RequestParam("occupation") String occupation, Model model) {
        List<Applicants> applicants = applicantService.findApplicantByOccupation(occupation);
        if (applicants.isEmpty()) {
            return "not_found_page";
        }
        model.addAttribute("applicants", applicants);
        return "result_by_occupation_page";
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
    public String updateStatusResult(@ModelAttribute("username") @Valid Applicants applicants, String username, BindingResult bindingResult) {
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
