package com.example.authservice.controller;

import com.example.authservice.dto.AuthRequest;
import com.example.authservice.entities.UserCredentiel;
import com.example.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String addUser(@RequestBody UserCredentiel userCredentiel) {
        return authService.saveUser(userCredentiel);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        return authService.generateToken(authRequest.getUsername());
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        authService.validateToken(token);
        return "valid";
    }

}
