package com.example.bcrypt.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/*
    By eliminating boilerplate code, Spring Boot Lombok enhances
      productivity, readability, and maintainability of projects.

      And the Validation is ensuring that the data provided through
      HTTP requests, meets certain criteria before it is processed.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Applicants {

    @Id
    @NotBlank(message = "is required")
    private String username;

    @NotBlank(message = "is required")
    private String password;


    @NotBlank(message = "is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "is required")
    @Size(min = 2, max = 50, message = "Surname must be between 2 and 50 characters")
    private String surname;

    @NotNull(message = "is required")
    @Positive(message = "Number must be positive")
    @Min(value = 18, message = "Your age must be at least 18")
    private int age;


    @NotBlank(message = "is required")
    private String occupation;

    @NotBlank(message = "is required")
    @Email
    private String email;

    @NotNull(message = "is required")
    @Min(value = 1, message = "Income must be at least 1")
    @Max(value = 2000, message = "Income must be at most 2000")
    private double income;

    @Pattern(regexp = "^.{0,1000}$", message = "Comments cannot exceed 1000 characters")
    private String comments;

    private String status;

    private boolean deletion;



/*   public void setPassword(String password) {
        // BcryptPasswordEncoder is used to hash the password before storing it
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    } */
}
