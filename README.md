#Project Overview

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


##Installation Guide

<<Detail the steps required to set up and run the application.
Include any prerequisites, such as software dependencies or database configurations.
Provide clear instructions for installation on different operating systems if applicable.>>

This guide will walk you through the steps to install and run the Java Spring Boot application for managing financial aid applications.

###Prerequisites
**Java Development Kit (JDK):** Install the latest version of JDK, which can be downloaded from the Oracle website or other JDK providers.
**Spring Boot:** Ensure you have the latest version of Spring Boot. It can be obtained through the Spring Initializr or by adding the dependency in your build configuration if you’re using Maven or Gradle.
**Integrated Development Environment (IDE):** An IDE like IntelliJ IDEA, Eclipse, or Spring Tool Suite (STS) for easier management of the project.
**Database:** A relational database such as MySQL or PostgreSQL. Make sure to install the database server and set it up before starting the application.
Maven or Gradle: For dependency management and running the Spring Boot application.

###Installation Steps
1. **Clone the Repository:** Clone the project repository from the version control system like Git.
```git clone https://github.com/albertcordina/Applicant-tracking-system.git```

2. **Open the Project:**

Open the project in your IDE and let it resolve all the dependencies.

3. **Build the Application:**
If using Maven:
```mvn clean install```

If using Gradle:
```gradle build```

4. **Run the Application:**
If using Maven:
```mvn spring-boot:run```

If using Gradle:
```gradle bootRun```

5. **Access the Application:** Once the application is running, you can access it via a web browser at http://localhost:8080

User Guide
<<Describe the user interface and functionality of the application.
Provide step-by-step instructions for users to navigate through the application and perform common tasks.
Include screenshots or diagrams to illustrate key features and workflows.>>
Overview
This guide will help you complete the registration form on our website. Follow the steps below to create your account successfully.

1. Access the Registration page

          A- Navigate to our website’s homepage.
          B- Click on the Register button usually located on the top right of the page.
   
2. Fill in Your Personal Information
   
          In this area you should fill all the information that is mentioned.
   
4. Submit the Form
   
          A- Once all fields are filled in the verified, click submit button.
          B- If there are any errors they will be highlighted. Please correct them and try submitting again.

 Contact Support
 
         If you encounter any issues or need further assistance, please contact our support 
         team through the “Contact us” page or via email at abcsupport@example.com.
 


Database Schema
<<Present the database schema used by the application, including tables, relationships, and data types.
Explain the purpose of each table and its attributes.>>

Here’s a more detailed description of our database schema featuring an “APPLICANT” table and an “AUTHORITIES” table:

Database Schema Overview
Our database schema is designed to manage information about applicants and the authorities associated with them.
This database schema consists of two primary tables: Applicant and Authorities. Below is an in-depth description of each table and their relationship to each other.

Applicant Table
Table Name : APPLICANT

The applicant table stores personal and applicant related details about individuals applying in our certain services. Each record represents a single applicant.

Columns:

    1- Username -   The applicant username, stored as a string and primary key. Links to the Authority username as 
                    foreign key in the authorities table to indicate which authority is handling the applicant case.
    2- Password -   The applicant password, stored as a string.
    3- Name -       The applicant name, stored as a string.
    4- Surname -    The applicant's surname, stored as a string.
    5- Age -        The applicant age, stored as an int (not under the 18).
    6- Occupation - The applicant occupation, stored as a string.
    7- Email -      The applicant email, stored as a string.
    8- Income -     The applicant income, stored as a double (not above the 2000).
    9- Comments -   The applicant comments, stored as a string.
    10- Status -    The current status of the application (e.g. Pending, Approved, Rejected), stored as a string type.

Authorities Table
Table Name: AUTHORITIES

The authorities table contains information about the authorities responsible for handling the application.  
Each record in the table represents an individual authority or an authoritative body.

Columns:

    1- Username - stored as a string primary key, linked to the applicant username as from the applicant table.
    2- Authority - stored as a string, and it is a unique identifier for each authority.

Data Flow

    1- Applicant  Submits Application - An individual fills out an application form the submit button, creating a new entry in the applicant table.
    2- Authority Assignment - The applicant is assigned to a specific authority, indicated by the setting the  “username” in both tables.
    3- Application Review - The assigned review of the application. The status is updated in the Applicant table to reflect the progress (e.g. Pending, Approved, Rejected).

This schema provides a foundational structure for managing applicants and the authorities responsible for processing their applications.




                                        Class and Method Documentation

The project Applicant tracking system consists of the 10 classes, 2 interfaces:

The 11 classes are:

 the main package of the project

  - GovernmentFinancialSupportApplicantion

        The “entry class” of the applicantion. Contains the ‘main’ method.

  - CSVExporter

        Contains the mthod exportApplicantsToCSV, which helps in saving applicants.CSV file
                    in 'target' folder of the project (see the AdminController class).


 the package ‘controller’
  
   - AccountController

         Calls the ApplicantRepository interface

         the endpoint methods @GetMapping:
           showAccountPage – calls the getCurrentLogedInUsername method and findByUsername to find user with its data by username.
           showManageAccountPage – shows the data of the Applicant (accessed from the applicant’s account)
            
         the method getCurrentLoggedInUsername – is retriving the username of the currently authenticated user without duplicating
                                                    the authentication logic in multiple places.
      

- AdminController
 
      calls the ApplicantService class and contains the initBinder method with @InitBinder annotation, which trims strings for all String fields

            the endpoint methods @GetMapping:
                 admin- get to the admin.html page
                 about_us –  get to the about_us.html page
                 manageApplicants – calls findAllApplicants method of the ApplicantService class and get to the manage_applicants.html page
              
            to delete applicant endpoint method @PostMethod:
                 deleteUser – calls findEmailByUsername of the ApplicantService class to fetch the applicant's email before deleting the account
                              calls deleteApplicantByUsername of the ApplicantService class to delete the applicant
                              calls sendEmail of the EmailService class to send the acknowledgement email to the applicant and gets to the success_page.html page

            to find applicant by AGE endpoints methods @GetMapping:
                 findByAgeGet – get to the find_by_age_page.html page
                 findByAgePost – calls findApplicantByAge method of the ApplicantService class and gets to the result_by_age_page.html page

            to find applicant by Occupation endpoints methods @GetMapping:
                 findByOccupationPage – get to the find_by_occupation_page.html page
                 findByOccupation – calls findApplicantByOccupation method of the ApplicantService class and gets to the result_by_occupation_page.html page

            to find the total number of the applicants endpoint method @GetMapping:
                 totalNumber – calls getTotalNumberOfApplicants method of the ApplicantService class and gets to the total_number_page.html page

            to update the status of the application endpoint methods @GetMapping:
                 updateStatusPage – calls findApplicantByUsername method of the ApplicantService class and gets to the update_status.html page.
                 updateStatus – calls saveApplicant method of the ApplicantService class and gets to the success_page.html page.

            to export the list of the users to CSV file endpoint method @PostMapping:
                 exportToCSVinProject – calls the findAllApplicants method of the ApplicantService class and exports all applicants to CSV file.


- ApplicantController
               
      Calls the ApplicantService class

          Contains the endpoint methods @GetMapping:
          home – get to the main_page.html page
          registration – get to the registration.html page
          login – get to the login_page.html page
          applicant – get to the applicant.html page
          news -get to the news.html page
   
          Applicant’s deletion request:

          showApplicantsDeletionRequest - for Admin review. It calls findApplicantsByDeletion method of the
                                          ApplicantService class and gets to the account_removal_list.html page.

          removeAccountRequestGetPage –   for Applicant to send the request. It calls findApplicantByUsername mehtod of
                                          the ApplicantService class and gets the Applicant to the account_removal_request.html page.

          removalAccountRequestSend –     sends the Applicant’s request, i.e. calls the saveApplicant method of the ApplicantService
                                          class and changes the boolean deletion to true, then gets to the success_page.html page.


          Update the Applicant’s data:

          updateApplicantPage –  for Applicants. It calls the findApplicantByUsername method of the ApplicantService class and gets to the update_form.html page.
          updateApplicant –      calls the saveApplicant method of the ApplicantService class and gets to success_page.html page.


- RegistrationController

      Calls the ApplicantRepository interface and SaveApplicant class. 
      Contains the initBinder method with @InitBinder annotation, which trims strings for all String fields.

           showRegistrationForm - @GetMapping endpoint method to get to the registration.html page.
           submitRegistration - @PostMapping endpoint method with the following functuions:
                 calls the existsByUsername method of the ApplicantRepository interface 
                 hash the password using Bcrypt before saving
                 calls the saveApplicant method of the SaveApplicant class
                 if the applicant is the age for retirement, i.e. > 67, AND did not register the occupation as retired- sends the Applicant to the retired_page.html page 
                 sends the Applicant to the registration_confirmation.html page.


 the package ‘model’:

- Applicants
  
                Entity class, @Entity with the Spring Boot Lombok, wich enhances productivity, readabiliy, and maintainability of the project.
                And the Validation is ensuring that the data provided through HTTP requests, meets certain criteria before it is processed.
                 Contains the parameter ‘username’ as the primary key and other parameters:
                      password, name, surname, age, occupation, email, income, comments, status and deletion.           

- Authorities
  
                Entity class, the same as the Applicants. It has the parameter ‘username’ as the primary key and also as the foreign key of the Applicant’s ‘username’.
                The parameter ‘authority’ for holding the Role of the Applicant is the second and last parameter of the Authorities class.
 
- SaveApplicant

       Calls the ApplicantRepository and AuthorityRepository interfaces.
       Contains the saveApplicant method which calls save methods of the Applicant and Authority Repositories.
                 The parameter ‘authority’ of the authorities table is set as ‘ROLE_APPLICANT’ by default.
                 The parameter ‘status’ of the applicants table is set as ‘On the review’ by default.
                 The parameter ‘deletion’ of the applicants table is set as ‘false’ by default.

the package ‘service’

 - ApplicantService

         Calls the ApplicantRepository and AuthorityRepository interfaces.
         Contains the methods:
  
                          saveApplicant –              calls the save method of the ApplicantRepository interface.
                          findAllApplicants –          calls the findAll method of the Applicant Repository interface.
                          findApplicantByUsername –    calls the findByUsername method of the ApplicantRepository interface.
                          findApplicantByAge –         calls the findByAge method of the ApplicantRepository interface.
                          findApplicantsByOccupation – calls the findByOccupation method of the ApplicantRepository interface.
                          findApplicantByDeletion –    calls the findByDeletionIsTrue method of the ApplicantRepository interface.
                          findEmailByUsername -        calls the findEmailByUsername of the ApplicantRepository interface.
                          deleteApplicantByUsername –  calls the deleteByUsername method of the AuthorityRepository interface and
                                                        then the deleteByUsername method of the ApplicantRepository interface.

       getTotalNumberOfApplicants – calls the findAll method of the ApplicantRepository and returns the number of elements in the list of the Applicants.

- EmailService

       Calls the JavaMailSender which provides a robust and flexible way to integrate email functionality into Spring application.
       Contains the method sendEmail which calls the SimpleMailMessage class of Spring framework with its methods;
                              setTo, setSubject setText, and calls the method send of JavaMailSender class
        

The 2 interfaces are:

 the package ‘repo’

 - ApplicantRepository extends the JpaRepository, i.e.  Spring Data JPA 

       Contains the following methods:
          
        existsByUsername, findByUsername, deleteByUsername, findByAge, findByOccupation and findByDeletionIsTrue
        findEmailByUsername - the method with @Query annotation, which gets the email according to the username of the applicant


 - AuthorityRepository extends the JpaRepository, i.e.  Spring Data JPA
 
       Contains the method deleteByUsername


Credits and Acknowledgments
<<Acknowledge any third-party libraries, frameworks, or resources used in the development of the project.
Give credit to team members for their contributions to different aspects of the project.>>



