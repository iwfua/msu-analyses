package ru.banksystem.training.kostyaback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.banksystem.training.kostyaback.domain.RefreshToken;
import ru.banksystem.training.kostyaback.domain.User;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    
    Optional<RefreshToken> findByToken(String token);
    
    Optional<RefreshToken> findByUser(User user);
    
    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.user = :user")
    void deleteByUser(User user);
    
    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.expiryDate <= CURRENT_TIMESTAMP")
    void deleteExpiredTokens();
    
    @Modifying
    @Query("UPDATE RefreshToken rt SET rt.token = :token, rt.expiryDate = :expiryDate WHERE rt.user = :user")
    int updateTokenForUser(@Param("user") User user, @Param("token") String token, @Param("expiryDate") Instant expiryDate);
}