package com.prep_saga.PrepSaga.security;

import com.prep_saga.PrepSaga.entity.User;
import com.prep_saga.PrepSaga.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
public class TokenValidationService {

    @Value("${google.oauth2.jwt.keyset.uri:https://oauth2.googleapis.com/tokeninfo}")
    private String googleKeysetUri;

    private final UserRepository userRepository;

    public TokenValidationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean validateToken(String idToken) {
        if (googleKeysetUri.isEmpty()) {
            System.out.println("Google Keyset URI is not configured.");
            return false;
        }

        String uri = UriComponentsBuilder.fromHttpUrl(googleKeysetUri)
                .queryParam("id_token", idToken)
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        try {
            String response = restTemplate.getForObject(uri, String.class);
            return response != null && response.contains("sub");
        } catch (Exception e) {
            System.out.println("Token validation failed: " + e.getMessage());
            return false;
        }
    }

    private void saveUserEmail(String email) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isEmpty()) {
            User user = new User();
            user.setEmail(email);
            userRepository.save(user);
        }
    }
}