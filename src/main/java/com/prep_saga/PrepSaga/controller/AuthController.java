package com.prep_saga.PrepSaga.controller;


import com.prep_saga.PrepSaga.entity.User;
import com.prep_saga.PrepSaga.model.RegisterRequest;
import com.prep_saga.PrepSaga.security.JwtTokenProvider;
import com.prep_saga.PrepSaga.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(AuthService authService, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @PutMapping("/add-admin")
    public ResponseEntity<?> addAdmin(@RequestParam String email) {
        User updatedUser = authService.addAdmin(email);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody User loginRequest) {
        return authService.login(loginRequest);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        return authService.verifyEmail(token);
    }

}