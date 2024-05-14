package com.example.bcrypt.repo;

import com.example.bcrypt.model.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authorities, Long> {

    void deleteByUsername(String username);
}
