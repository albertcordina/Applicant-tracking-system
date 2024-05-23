package com.example.bcrypt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
public class Authorities {

    @Id
    private String username;
    private String authority;
}