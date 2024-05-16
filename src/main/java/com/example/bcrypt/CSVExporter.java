package com.example.bcrypt;

import com.example.bcrypt.model.Applicants;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CSVExporter {

    // helps in saving applicants.CSV file in 'target' folder of the project (see the AdminController).
    public static void exportApplicantsToCSV (FileWriter writer, List<Applicants> applicants) throws IOException {
        writer.write("Username,Name,Surname,Age,Occupation,Email,Income,Comments,Status\n");
        for (Applicants applicant : applicants) {
            writer.write(applicant.getUsername() + ","
                    + applicant.getName() + ","
                    + applicant.getSurname() + ","
                    + applicant.getAge() + ","
                    + applicant.getOccupation() + ","
                    + applicant.getEmail() + ","
                    + applicant.getIncome() + ","
                    + applicant.getComments() + ","
                    + applicant.getStatus() + "\n");
        }
        writer.flush();
    }
}


/*
    //  helps in saving applicants.CSV file in PC downloads (see the AdminController).
    public static void exportApplicantsToCSV (PrintWriter writer, List<Applicants> applicants) {
        writer.println("Username,Name,Surname,Age,Occupation,Email,Income,Comments,Status");
        for (Applicants applicant : applicants) {
            writer.println(applicant.getUsername() + ","
                    + applicant.getName() + ","
                    + applicant.getSurname() + ","
                    + applicant.getAge() + ","
                    + applicant.getOccupation() + ","
                    + applicant.getEmail() + ","
                    + applicant.getIncome() + ","
                    + applicant.getComments() + ","
                    + applicant.getStatus());
        }
    }

 */