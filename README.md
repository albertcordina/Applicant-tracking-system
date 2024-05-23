                                                  Project Overview

Purpose: The primary purpose of this project is to create a platform where individuals can register and apply for financial government support. 
         It aims to streamline the process of collecting user information, submitting applications, and enabling administrators to efficiently manage and respond to these applications.

Goals:

         To provide a user-friendly interface for users to register and submit their financial government support applications.
         To ensure secure handling of personal information such as age, employment status, and other relevant details.
         To enable administrators to filter and review applications based on various criteria to streamline the process and make informed decisions.
         To automate part of the process of application review to increase efficiency and reduce manual errors.
         
Intended Audience:

         Users: Individuals seeking financial government support who are comfortable with 
                using online platforms to submit personal information and applications.
         Administrators: Personnel who will oversee the application process, review user 
                         information, and make decisions on the disbursement of aid.

This system is designed to be scalable and secure, ensuring that it can handle a growing number of users and sensitive data with integrity. 
The project will also focus on compliance with data protection regulations to maintain user trust and legal standards.

<<Provide a brief introduction to the technologies used and the architectural design of the application.>>
Installation Guide
<<Detail the steps required to set up and run the application.
Include any prerequisites, such as software dependencies or database configurations.
Provide clear instructions for installation on different operating systems if applicable.>>

User Guide
<<Describe the user interface and functionality of the application.
Provide step-by-step instructions for users to navigate through the application and perform common tasks.
Include screenshots or diagrams to illustrate key features and workflows.>>
Overview
This guide will help you complete the registration form on our website. Follow the steps below to create your account successfully.

1- Access the Registration page
   A- Navigate to our website’s homepage.
   B- Click on the Register button usually located on the top right of the page.
2. Fill in Your Personal Information
    In this area you should fill all the information that is mentioned.
3. Submit the Form
   A- Once all fields are filled in the verified, click submit button.
   B- If there are any errors they will be highlighted. Please correct them and try submitting again.

Contact Support
  If you encounter any issues or need further assistance, please contact our support team through the “Contact us” page or via email at
abcsupport@example.com.



Database Schema
<<Present the database schema used by the application, including tables, relationships, and data types.
Explain the purpose of each table and its attributes.>>
Certainly Here’s a more detailed description of our database schema featuring an “APPLICANT” table and an “AUTHORITIES” table:
Database Schema Overview
Our database schema is designed to manage information about applicants and the authorities associated with them.
This database schema consists of two primary tables: Applicant and Authorities. Below is an in-depth description of each table and their relationship to each other.

Applicant Table
Table Name : APPLICANT
The applicant table stores personal and applicant related details about individuals applying in our certain services. Each record represents a single applicant.


Column:
1- Username - The applicant username, stored as a string. Links to the Authority username in the authorities table to indicate which authority is handling the applicant case.
2- Password - The applicant password, stored as a string.
3- Name - The applicant name, stored as a string.
4- Surname - The applicant's surname, stored as a string.
5- Age -  The applicant age, stored as an int.
6- Occupation - The applicant occupation, stored as a string.
7- Email - The applicant email, stored as a string.
8- Income - The applicant income, stored as a double.
9- Comments - The applicant comments, stored as a string.
10- Status - The current status of the application (e.g. Pending, Approved, Rejected), stored as a string type.

Authorities Table
Table Name: Authorities
The authorities table contains information about the authorities responsible for handling the application.  Each record in the table represents an individual authority or an authoritative body.
Column:
1- Username - stored as a string, linked to the applicant username from the applicant table.
2- Authority - stored as a string, and it is a unique identifier for each authority.

Data Flow
1- Applicant  Submits Application - An individual fills out an application form the submit button, creating a new entry in the applicant table.
2- Authority Assignment - The applicant is assigned to a specific authority, indicated by the setting the  “username” in both tables.
3- Application Review - The assigned review of the application. The status is updated in the Applicant table to reflect the progress (e.g. Pending, Approved, Rejected).

This schema provides a foundational structure for managing applicants and the authorities responsible for processing their applications.




                                        Class and Method Documentation
<<Document classes and methods comprehensively, including their purpose, inputs, outputs, and any side effects.>>      

The whole code of the project Applicant tracking system consists of the 10 classes, 2 interfaces and 24 html pages:

The 10 classes are:

 The main package of the project

    - GovernmentFinancialSupportApplicantion

         the “entry class” of the application (contains the ‘main’ method);

    - CSVExporter

         contains the method exportApplicantsToCSV method which helps in saving 
         applicants.CSV file in the 'target' folder of the project (see the AdminController class);

 The package ‘controller’

    - AccountController

         calls the ApplicantRepository interface;

         contains the endpoint methods @GetMapping:
            showAccountPage – calls the getCurrentLogedInUsername method and 
                                              findByUsername to find a user with its data by username.
           showManageAccountPage – shows the data of the Applicant (accessed from the
                                                             applicant’s account)

        The method get CurrentLoggedInUsername – is retrieving the username of the currently 
                        authenticated user without duplicating the authentication logic in multiple places.


Credits and Acknowledgments
<<Acknowledge any third-party libraries, frameworks, or resources used in the development of the project.
Give credit to team members for their contributions to different aspects of the project.>>



