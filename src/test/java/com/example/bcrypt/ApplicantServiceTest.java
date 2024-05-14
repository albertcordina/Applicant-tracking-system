package com.example.bcrypt;

import com.example.bcrypt.model.Applicants;
import com.example.bcrypt.repo.ApplicantRepository;
import com.example.bcrypt.repo.AuthorityRepository;
import com.example.bcrypt.service.ApplicantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApplicantServiceTest {

    @Mock
    private ApplicantRepository applicantRepository;

    @Mock
    private AuthorityRepository authorityRepository;

    @InjectMocks
    private ApplicantService applicantService;

    @Test
    public void testSaveApplicant() {
        Applicants applicant = new Applicants(); // Create a sample applicant
        applicantService.saveApplicant(applicant);
        verify(applicantRepository, times(1)).save(applicant);
    }

    @Test
    public void testFindAllApplicants() {

        List<Applicants> applicants = new ArrayList<>();
        applicants.add(new Applicants()); // Add sample applicants to the list
        applicants.add(new Applicants());

        when(applicantRepository.findAll()).thenReturn(applicants);

        List<Applicants> result = applicantService.findAllApplicants();

        assertEquals(2, result.size());
    }

    @Test
    public void testFindApplicantByUsername() {
        String username = "testUsername";
        Applicants applicant = new Applicants(); // Create a sample applicant
        when(applicantRepository.findByUsername(username)).thenReturn(applicant);

        Applicants result = applicantService.findApplicantByUsername(username);

        assertEquals(applicant, result);
    }

    @Test
    public void testDeleteApplicantByUsername() {
        String username = "testUsername";
        applicantService.deleteApplicantByUsername(username);
        verify(authorityRepository, times(1)).deleteByUsername(username);
        verify(applicantRepository, times(1)).deleteByUsername(username);
    }

    @Test
    public void testGetTotalNumberOfApplicants() {
        List<Applicants> applicants = new ArrayList<>();
        applicants.add(new Applicants()); // Add sample applicants to the list
        applicants.add(new Applicants());

        when(applicantRepository.findAll()).thenReturn(applicants);

        int result = applicantService.getTotalNumberOfApplicants();

        assertEquals(2, result);
    }
}
