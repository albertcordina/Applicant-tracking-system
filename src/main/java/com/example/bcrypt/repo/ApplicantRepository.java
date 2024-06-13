package com.example.bcrypt.repo;

import com.example.bcrypt.model.Applicants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
/*
    NOTE: the name of the method should start with the default Spring data JPA CRUD method/function,
          and then the name of the column, e.g. 'findByAge', i.e. 'findBy' + 'Age'.
 */
public interface ApplicantRepository extends JpaRepository<Applicants, String> {

    boolean existsByUsername(String username);
    Applicants findByUsername(String username);
    void deleteByUsername(String username);
    List<Applicants> findByAge (Integer age);
    List<Applicants> findByAgeBetween(Integer minAge, Integer maxAge);
    List<Applicants> findByIncome(Double income);
    List<Applicants> findByIncomeBetween(Double minIncome, Double maxIncome);
    List<Applicants> findByOccupation (String occupation);
    List<Applicants> findByDeletionIsTrue();

    /*
      The @Query annotation in Spring Data JPA is used to define custom JPQL (Java Persistence Query Language)
       or native SQL queries directly on repository methods. This annotation allows us to specify queries
       directly within our repository interface without needing to create named queries
                 or use the query methods derived from method names.
       We use @Query because we need to get one parameter according to another parameter, i.e. the two columns involved (email and username).
     */
    @Query("SELECT a.email FROM Applicants a WHERE a.username = :username")
    String findEmailByUsername(@Param("username") String username);
}

