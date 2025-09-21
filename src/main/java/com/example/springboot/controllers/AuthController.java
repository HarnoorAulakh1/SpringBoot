package com.example.springboot.controllers;


import com.example.springboot.models.User;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authManager,
                          JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    // --- Registration ---
    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password) {
        if(userRepository.findByUsername(username).isPresent()) {
            return "Username already exists";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("ROLE_USER");
        userRepository.save(user);

        return "User registered successfully";
    }

    // --- Login ---
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username,
                                   @RequestParam String password,
                                   HttpServletResponse response) {
        // Authenticate
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        String token = jwtUtil.generateToken(username);

        // --- Set JWT in a cookie ---
        Cookie cookie = new Cookie("JWT_TOKEN", token);
        cookie.setHttpOnly(true);           // Cannot be accessed via JavaScript (safer)
        cookie.setSecure(true);             // Only over HTTPS (set false for local dev)
        cookie.setPath("/");                // Cookie valid for all paths
        cookie.setMaxAge(60 * 60);          // 1 hour
        response.addCookie(cookie);

        return ResponseEntity.ok("Login successful");
    }
}

