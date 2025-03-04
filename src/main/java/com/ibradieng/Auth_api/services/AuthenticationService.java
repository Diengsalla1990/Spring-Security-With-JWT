package com.ibradieng.Auth_api.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ibradieng.Auth_api.dtos.LoginUserDto;
import com.ibradieng.Auth_api.dtos.RegisterUserDto;
import com.ibradieng.Auth_api.entities.User;
import com.ibradieng.Auth_api.repositories.UserRepository;

@Service
public class AuthenticationService {
private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
        UserRepository userRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {
        User user = new User();
                //.setFullName(input.getFullName())
                //.setEmail(input.getEmail())
                //.setPassword(passwordEncoder.encode(input.getPassword()));
        
		        user.setEmail(input.getEmail());
		        user.setFullName(input.getFullName());
		        user.setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }

}
