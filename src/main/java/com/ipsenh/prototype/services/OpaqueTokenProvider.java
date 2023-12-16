package com.ipsenh.prototype.services;

import com.ipsenh.prototype.model.Token;
import com.ipsenh.prototype.repositories.AccountRepository;
import com.ipsenh.prototype.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OpaqueTokenProvider {

    @Autowired
    TokenRepository tokenRepository;

    public Token generateToken(String username){
        Token token = new Token(username);
        try {
            tokenRepository.save(token);
            return token;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean isTokenValid(UUID tokenId){
        Token token;
        try{
            token = tokenRepository.findByTokenId(tokenId);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expTime = token.getExpiresAt();
        return currentTime.isBefore(expTime);
    }

}
