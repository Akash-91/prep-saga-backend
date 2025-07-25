package com.prep_saga.PrepSaga.config;

import com.prep_saga.PrepSaga.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Controller
public class OAuth2LoginController {

    private JwtTokenProvider jwtTokenProvider;

    public OAuth2LoginController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @RequestMapping("/login/oauth2/code/google")
    public ResponseEntity<Void> oauth2LoginCallback(Authentication authentication, HttpServletResponse response) {
        // OAuth2User from Spring Security
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

        // Generate JWT token for the authenticated user
        String token = jwtTokenProvider.createTokenFromOAuth2User(oauth2User);
        System.out.println("Generated Token: " + token);

        // Redirect to the frontend with the JWT token in the query string
        response.setStatus(HttpServletResponse.SC_FOUND);
        response.setHeader("Location", "http://localhost:3000/oauth2/callback?token=" + token);

        return new ResponseEntity<>(HttpStatus.FOUND); // HTTP 302 for redirect
    }
}
