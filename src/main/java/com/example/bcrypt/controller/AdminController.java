package com.example.bcrypt.controller;

import com.example.bcrypt.model.Applicants;
import com.example.bcrypt.service.ApplicantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private ApplicantService applicantService;

    public AdminController(ApplicantService applicantService) {this.applicantService = applicantService;}

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Trim strings for all String fields
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping("/admin")
    public String admin () {
        return "admin";
    }

    @GetMapping("/about_us")
    public String aboutUs() {return "about_us";}

    @GetMapping("/manageApplicants")
    public String manageApplicants(Model model) {
        model.addAttribute("applicants", applicantService.findAllApplicants());
        return "manage_applicants";}

    //---------------- delete Applicant -------------------

    @GetMapping("/deleteApplicant")
    public String deleteUserGet(@RequestParam("username") String username) {
        applicantService.deleteApplicantByUsername(username);
        return "success_page";
    }

    @PostMapping("/deleteApplicant")
    public String deleteUserPost(@RequestParam("username") String username) {
        applicantService.deleteApplicantByUsername(username);
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
    public String findByOccupationPage() { return "find_by_occupation_page";}

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
        int totalNumberOfApplicants = applicantService.getTotalNumberOfApplicants();
        model.addAttribute("totalNumberOfApplicants", totalNumberOfApplicants);
        return "total_number_page";
    }
    //------------------------------ update the Status of the Application ---------------------------------

    @GetMapping("/updateStatusGetPage")
    public String updateStatusPage (@RequestParam("username") String username, Model model) {
        Applicants applicants = applicantService.findApplicantByUsername(username);
        model.addAttribute("applicants", applicants);
        return "update_status";
    }

    @GetMapping("/updateStatus")
    public String updateStatus (@ModelAttribute("username") @Valid Applicants applicants, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update_status";
        }
        applicantService.saveApplicant(applicants);
        return "success_page";
    }
}
