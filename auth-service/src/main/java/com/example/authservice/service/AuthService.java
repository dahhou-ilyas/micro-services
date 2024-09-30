package com.example.authservice.service;

import com.example.authservice.entities.UserCredentiel;
import com.example.authservice.repository.UserCredentielRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserCredentielRepository userCredentielRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String saveUser(UserCredentiel userCredentiel) {
        userCredentiel.setPassword(passwordEncoder.encode(userCredentiel.getPassword()));
        userCredentielRepository.save(userCredentiel);
        return userCredentiel.getUsername();
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }


}
