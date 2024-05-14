package com.example.bcrypt.service;

import com.example.bcrypt.model.Applicants;
import com.example.bcrypt.repo.ApplicantRepository;
import com.example.bcrypt.repo.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApplicantService {

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    public void saveApplicant (Applicants applicants) {applicantRepository.save(applicants);}

    public List<Applicants> findAllApplicants (){ return applicantRepository.findAll();}

    public Applicants findApplicantByUsername(String username){ return applicantRepository.findByUsername(username);}

    public List<Applicants> findApplicantByAge (Integer age){ return applicantRepository.findByAge(age);}

    public List<Applicants> findApplicantByOccupation (String occ){ return applicantRepository.findByOccupation(occ);}

    public List<Applicants> findApplicantsByDeletion() {
        return applicantRepository.findByDeletionIsTrue();
    }

/*
      @Transactional: the method is executed within a transaction managed by Spring.
      If any exception occurs during method execution, the transaction will be rolled back, ensuring data consistency.
 */
    @Transactional
    public void deleteApplicantByUsername(String username) {
        // Delete related authorities first
        authorityRepository.deleteByUsername(username);

        // Then delete the applicant
        applicantRepository.deleteByUsername(username);
    }

    /*
        The method gets the total number of the applicants.
     */
    public int getTotalNumberOfApplicants () {
        List<Applicants> applicants = applicantRepository.findAll();
        return applicants.size();
    }
}
