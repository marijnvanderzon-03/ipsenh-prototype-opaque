package com.ipsenh.prototype.repositories;

import com.ipsenh.prototype.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {
    Token findByTokenId(UUID tokenId);

    void deleteByExpiresAtBefore(LocalDateTime currentTime);
}

