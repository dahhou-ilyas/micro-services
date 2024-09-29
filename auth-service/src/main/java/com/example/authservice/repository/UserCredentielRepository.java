package com.example.authservice.repository;

import com.example.authservice.entities.UserCredentiel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCredentielRepository extends JpaRepository<UserCredentiel, Long> {
}
