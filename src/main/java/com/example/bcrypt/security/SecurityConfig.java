package com.example.bcrypt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration // for configuration of security function.
public class SecurityConfig {


    @Bean // for decoding password. Encoding is done by setPassword() method.
    public PasswordEncoder passwordEncoder() { // for decoding password.
        return new BCryptPasswordEncoder();
    }

    @Bean // for communication with database.
    public UserDetailsManager userDetails(DataSource dataSource) { // for connection with database.

        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);

        userDetailsManager.setUsersByUsernameQuery("select username, password, enabled from applicants where username=?");
        userDetailsManager.setAuthoritiesByUsernameQuery("select username, authority from authorities where username=?");

        return userDetailsManager;
    }

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(config -> config

                        .requestMatchers("/").permitAll()
                        .requestMatchers("/home").permitAll()
                        .requestMatchers("/form").permitAll() // endpoint '/form' is for the registration.
                        .requestMatchers("/confirmation").permitAll()
                        .requestMatchers("/submitData").permitAll()
                        .requestMatchers("/about_us").permitAll()
                        .requestMatchers("/applicantsByAge").permitAll()

                        .anyRequest().authenticated() // for login and logout not need to be authenticated.

                )

                .formLogin(form -> form.loginPage("/login")
                        .loginProcessingUrl("/authenticate")
                        .defaultSuccessUrl("/account", true) // Redirect to account page upon successful login.
                        .permitAll()
                )
                .logout(logout -> logout.permitAll()
                );
        
        return http.build();
    }
}
