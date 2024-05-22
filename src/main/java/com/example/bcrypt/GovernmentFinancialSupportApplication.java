package com.example.bcrypt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
  @SpringBootApplication is an annotation that combines @Configuration, @EnableAutoConfiguration, and @ComponentScan.
   It's used to mark the main class of a Spring Boot application and enables autoconfiguration and component scanning.
 */
@SpringBootApplication
public class GovernmentFinancialSupportApplication {

	public static void main(String[] args) {
		SpringApplication.run(GovernmentFinancialSupportApplication.class, args);
	}
}

