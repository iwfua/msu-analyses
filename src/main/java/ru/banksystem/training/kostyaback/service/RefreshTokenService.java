package ru.banksystem.training.kostyaback.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.banksystem.training.kostyaback.domain.RefreshToken;
import ru.banksystem.training.kostyaback.domain.User;
import ru.banksystem.training.kostyaback.repository.RefreshTokenRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    
    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenDurationMs;
    
    private final RefreshTokenRepository refreshTokenRepository;
    
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
    
    @Transactional
    public RefreshToken createRefreshToken(User user) {
        String newToken = UUID.randomUUID().toString();
        Instant newExpiryDate = Instant.now().plusMillis(refreshTokenDurationMs);
        
        // Try to update existing token first
        int updatedRows = refreshTokenRepository.updateTokenForUser(user, newToken, newExpiryDate);
        
        if (updatedRows > 0) {
            // Token was updated, fetch and return it
            return refreshTokenRepository.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Failed to retrieve updated refresh token"));
        } else {
            // No existing token found, create a new one
            RefreshToken refreshToken = RefreshToken.builder()
                    .user(user)
                    .expiryDate(newExpiryDate)
                    .token(newToken)
                    .build();
            
            try {
                return refreshTokenRepository.save(refreshToken);
            } catch (Exception e) {
                // Handle potential race condition - try to update again
                int retryUpdatedRows = refreshTokenRepository.updateTokenForUser(user, newToken, newExpiryDate);
                if (retryUpdatedRows > 0) {
                    return refreshTokenRepository.findByUser(user)
                            .orElseThrow(() -> new RuntimeException("Failed to retrieve refresh token after retry"));
                }
                throw new RuntimeException("Failed to create or update refresh token: " + e.getMessage(), e);
            }
        }
    }
    
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.isExpired()) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token истек. Пожалуйста, войдите заново");
        }
        return token;
    }
    
    @Transactional
    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }

    //TODO сделать шедулер для очистки
    @Transactional
    public void deleteExpiredTokens() {
        refreshTokenRepository.deleteExpiredTokens();
    }
}