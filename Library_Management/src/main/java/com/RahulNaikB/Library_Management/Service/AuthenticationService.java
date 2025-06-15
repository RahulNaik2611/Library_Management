package com.RahulNaikB.Library_Management.Service;

import com.RahulNaikB.Library_Management.DTO.LoginRequestDTO;
import com.RahulNaikB.Library_Management.DTO.LoginResponseDTO;
import com.RahulNaikB.Library_Management.DTO.RegisterRequestDTO;
import com.RahulNaikB.Library_Management.Entity.User;
import com.RahulNaikB.Library_Management.JWT.JwtService;
import com.RahulNaikB.Library_Management.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;  // ✅ Make sure you have this service implemented

    public User registerNormalUser(RegisterRequestDTO registerRequestDTO) {
        if (userRepository.findByUserName(registerRequestDTO.getUsername()).isPresent()) {
            throw new RuntimeException("User already registered");
        }

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER"); // ✅ Corrected spelling and case for Spring Security

        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public User registerAdminUser(RegisterRequestDTO registerRequestDTO) {
        if (userRepository.findByUserName(registerRequestDTO.getUsername()).isPresent()) {
            throw new RuntimeException("User already registered");
        }

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_ADMIN"); // ✅ Corrected
        roles.add("ROLE_USER");

        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUsername(),
                        loginRequestDTO.getPassword()
                )
        );

        // Fetch the authenticated user
        User user = userRepository.findByUserName(loginRequestDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate JWT
        String token = jwtService.generateToken(user);  // ✅ fixed spelling from `generateToke`

        // Return response
        return LoginResponseDTO.builder()
                .token(token)
                .username(user.getUsername())
                .roles(user.getRoles())
                .build(); // ✅ Fixed incorrect method chaining
    }
}
