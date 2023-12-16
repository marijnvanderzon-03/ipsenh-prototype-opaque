package com.ipsenh.prototype.services;

import com.ipsenh.prototype.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenCleanupService {
    @Autowired
    TokenRepository tokenRepository;

    @Scheduled(fixedRate = 3600000) // 1 hour
    public void removeExpiredTokens() {
        LocalDateTime currentTime = LocalDateTime.now();

        // Assuming you have a method in your repository to delete tokens by ID
        // For example, if you are using Spring Data JPA repository:
        tokenRepository.deleteByExpiresAtBefore(currentTime);
    }
}
