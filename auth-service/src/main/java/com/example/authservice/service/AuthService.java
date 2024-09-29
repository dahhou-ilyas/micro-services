package com.example.authservice.service;

import com.example.authservice.entities.UserCredentiel;
import com.example.authservice.repository.UserCredentielRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserCredentielRepository userCredentielRepository;

    public String saveUser(UserCredentiel userCredentiel) {
        userCredentielRepository.save(userCredentiel);
        return userCredentiel.getUsername();
    }


}
