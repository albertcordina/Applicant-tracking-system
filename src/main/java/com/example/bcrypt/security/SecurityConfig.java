package com.example.bcrypt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

/*
    We centralize our security configuration, making it easier to manage and understand.
    This approach avoids potential conflicts and ensures all security-related settings are in one place.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {  // Configuration class for security functionality

    // Bean for encoding passwords using BCryptPasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* Bean for configuring applicant details service with JDBC */
    @Bean
    public UserDetailsService applicantsDetails(DataSource dataSource) {
        // Creates a JDBC applicants details manager
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        // Sets the SQL query to retrieve applicants by username
        userDetailsManager.setUsersByUsernameQuery("select username, password, enabled from applicants where username=?");
        // Sets the SQL query to retrieve applicant authorities by username
        userDetailsManager.setAuthoritiesByUsernameQuery("select username, authority from authorities where username=?");
        // Returns the configured applicant details manager
        return userDetailsManager;
    }

    /* Bean for configuring security filter chain */
    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {
        // Configures authorization rules for HTTP requests
        http.authorizeHttpRequests(config -> config
                        .requestMatchers("/", "/home", "/form", "/confirmation", "/submitData", "/about_us", "/applicantsByAge").permitAll()
                        .requestMatchers("/2fa", "/verify-2fa", "/login").permitAll()
                        .anyRequest().authenticated()
                )
                // Configures form-based authentication
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/authenticate")
                        .defaultSuccessUrl("/account", true)  // Redirect to account page upon successful login.
                        .permitAll()
                )
                // Configures logout functionality
                .logout(logout -> logout.permitAll());

        return http.build();
    }
}
