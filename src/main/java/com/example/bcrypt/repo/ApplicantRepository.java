package com.example.bcrypt.repo;

import com.example.bcrypt.model.Applicants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/*
    NOTE: the name of the method should start with the default Spring data JPA function,
          and then the name of the column, e.g. 'findByAge', i.e. 'findBy' + 'Age'.
 */
public interface ApplicantRepository extends JpaRepository<Applicants, String> {

    boolean existsByUsername(String username);
    Applicants findByUsername(String username);
    void deleteByUsername(String username);
    List<Applicants> findByAge (int age);
    List<Applicants> findByOccupation (String occupation);
    List<Applicants> findByDeletionIsTrue();
}
