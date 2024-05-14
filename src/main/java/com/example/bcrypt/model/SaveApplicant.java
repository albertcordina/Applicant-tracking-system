package com.example.bcrypt.model;

import com.example.bcrypt.repo.ApplicantRepository;
import com.example.bcrypt.repo.AuthorityRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class SaveApplicant {

    private final ApplicantRepository applicantRepository;
    private final AuthorityRepository authorityRepository;

    public SaveApplicant(ApplicantRepository applicantRepository, AuthorityRepository authorityRepository) {
        this.applicantRepository = applicantRepository;
        this.authorityRepository = authorityRepository;
    }

    public void saveApplicant (@Valid Applicants applicant) {

        Applicants applicants = new Applicants();

        applicants.setUsername(applicant.getUsername());
        applicants.setPassword(applicant.getPassword());

        applicants.setName(applicant.getName());
        applicants.setSurname(applicant.getSurname());
        applicants.setAge(applicant.getAge());
        applicants.setOccupation(applicant.getOccupation());

        applicants.setEmail(applicant.getEmail());
        applicants.setIncome(applicant.getIncome());

        applicants.setComments(applicant.getComments());
        applicants.setStatus("On the review");
        applicants.setDeletion(false);

        // Save user to the applicants table
        applicantRepository.save(applicants);

        // Save user's authority to the authorities table
        Authorities authority = new Authorities(applicants.getUsername(), "ROLE_APPLICANT");
        authorityRepository.save(authority);
    }
}



