package com.prep_saga.PrepSaga.service.auth;


import com.prep_saga.PrepSaga.entity.User;

import com.prep_saga.PrepSaga.entity.VerificationToken;
import com.prep_saga.PrepSaga.model.LoginResponse;
import com.prep_saga.PrepSaga.model.RegisterRequest;
import com.prep_saga.PrepSaga.model.Role;
import com.prep_saga.PrepSaga.model.UserStatus;
import com.prep_saga.PrepSaga.repository.TokenRepository;
import com.prep_saga.PrepSaga.repository.UserRepository;
import com.prep_saga.PrepSaga.security.JwtTokenProvider;
import com.prep_saga.PrepSaga.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;
    private final MailService mailService;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, TokenRepository tokenRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenRepository = tokenRepository;
        this.mailService = mailService;
    }

    /**
     * Register User
     *
     * @param registerRequest
     * @return
     */
    public ResponseEntity<?> register(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            return new ResponseEntity<>("User with this email already exists", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        // Encode password and set status
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setRole(Role.USER);
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);

        // ✅ Generate and save token
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, user, LocalDateTime.now().plusHours(24));
        tokenRepository.save(verificationToken);

        // ✅ Send actual token, not full message
        mailService.sendVerificationEmail(user.getEmail(), user.getUserName(), token); // second param is username or email

        return new ResponseEntity<>("User registered successfully. Please check your email to verify.", HttpStatus.OK);
    }

    /**
     * @param email
     * @return
     */
    public User addAdmin(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(Role.ADMIN);
        return userRepository.save(user);
    }


    /**
     * Authenticate User
     *
     * @param loginRequest
     * @return
     */
    public ResponseEntity<?> login(User loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + loginRequest.getEmail()));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

        if (user.getStatus() != UserStatus.ACTIVE) {
            return new ResponseEntity<>("Please verify your email before logging in.", HttpStatus.UNAUTHORIZED);
        }

        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRole().name());
        LoginResponse response = new LoginResponse("Bearer " + token, user.getEmail(), user.getRole());
        return ResponseEntity.ok(response);
    }

    /**
     * @param token
     * @return
     */
    public ResponseEntity<String> verifyEmail(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Token expired");
        }

        User user = verificationToken.getUser();
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);

        tokenRepository.delete(verificationToken);

        return ResponseEntity.ok("Email verified successfully!");
    }

}
