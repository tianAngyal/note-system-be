package com.angyal.app.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angyal.app.Models.User;
import com.angyal.app.Repository.UserRepository;
import com.angyal.app.Security.JwtUtil;

@Service
public class UserInfoVerificationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;
 

   
    public boolean userExists(String username) {
        return userRepository.existsByName(username);
    }

    
    public String authenticate(String username, String rawPassword) {
        Optional<User> user = userRepository.authenticate(username, rawPassword);

        if (user.isPresent()) {
            // Generovanie JWT tokenu
            return jwtUtil.generateToken(username);
        }

        throw new IllegalArgumentException("Nesprávne meno alebo heslo!");
    }

    
    public User registerNewUser(String username, String password) {
        if (userRepository.existsByName(username)) {
            throw new IllegalArgumentException("Užívateľ s týmto menom už existuje!");
        }

        return userRepository.register(username, password);
    }

   
    public boolean validateToken(String token) {
        return jwtUtil.isTokenValid(token);
    }

    
    public String extractUsernameFromToken(String token) {
        return jwtUtil.extractUsername(token);
    }

    
    public String generateToken(String username) {
        if (!userRepository.existsByName(username)) {
            throw new IllegalArgumentException("Užívateľ neexistuje!");
        }
        return jwtUtil.generateToken(username);
    }
}
