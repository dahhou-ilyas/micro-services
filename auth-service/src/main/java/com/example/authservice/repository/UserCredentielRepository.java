package com.example.authservice.repository;

import com.example.authservice.entities.UserCredentiel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentielRepository extends JpaRepository<UserCredentiel, Long> {
    Optional<UserCredentiel> findByUsername(String username);
}
