package com.prep_saga.PrepSaga.service.token;

import com.prep_saga.PrepSaga.entity.VerificationToken;
import com.prep_saga.PrepSaga.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.time.LocalDateTime;

@Service
public class TokenCleanupService {

    @Autowired
    private TokenRepository tokenRepository;

    //    @Scheduled(cron = "0 0 * * * *") // every hour
    @Scheduled(fixedRate = 3600000)
    public void deleteExpiredTokens() {
        List<VerificationToken> expired = tokenRepository.findAll()
                .stream()
                .filter(token -> token.getExpiryDate().isBefore(LocalDateTime.now()))
                .toList();
        tokenRepository.deleteAll(expired);
    }
}
