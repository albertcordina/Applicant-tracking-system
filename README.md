                                                         Project Overview

Purpose: The primary purpose of this project is to create a platform where individuals can register and apply for financial government support. 
         It aims to streamline the process of collecting user information, submitting applications, and enabling administrators to efficiently manage and respond to these applications.

Goals:

         To provide a user-friendly interface for users to register and submit their financial government support applications.
         To ensure secure handling of personal information such as age, employment status, and other relevant details.
         To enable administrators to filter and review applications based on various criteria to streamline the process and make informed decisions.
         To automate part of the process of application review to increase efficiency and reduce manual errors.
         
Intended Audience:

         Users: Individuals seeking financial government support who are comfortable with using online platforms to submit personal information and applications.
         Administrators: Personnel who will oversee the application process, review user information, and make decisions on the disbursement of aid.

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
