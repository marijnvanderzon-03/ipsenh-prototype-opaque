package com.ipsenh.prototype.model;

import com.ipsenh.prototype.repositories.TokenRepository;
import jakarta.persistence.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "token")
public class Token {

    @Id
    private UUID tokenId;
    String username;
    LocalDateTime IssuedAt;
    LocalDateTime expiresAt;

    public Token(String username){
        this.tokenId = UUID.randomUUID();
        this.username = username;
        this.IssuedAt = LocalDateTime.now();
        this.expiresAt = this.IssuedAt.plusHours(1);
    }

    public Token() {
    }

    public UUID getTokenId() {
        return tokenId;
    }

    public void setTokenId(UUID tokenId) {
        this.tokenId = tokenId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getIssuedAt() {
        return IssuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        IssuedAt = issuedAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}
