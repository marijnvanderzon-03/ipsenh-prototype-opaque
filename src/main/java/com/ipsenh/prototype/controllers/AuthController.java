package com.ipsenh.prototype.controllers;

import com.ipsenh.prototype.Payloads.SignupRequest;
import com.ipsenh.prototype.model.Account;
import com.ipsenh.prototype.Payloads.LoginRequest;
import com.ipsenh.prototype.model.Token;
import com.ipsenh.prototype.repositories.AccountRepository;
import com.ipsenh.prototype.services.AuthService;
import com.ipsenh.prototype.services.EncryptionService;
import com.ipsenh.prototype.services.OpaqueTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping
public class AuthController {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AuthService authService;
    @Autowired
    EncryptionService encryptionService;
    @Autowired
    OpaqueTokenProvider opaqueTokenProvider;


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, @RequestHeader(name = "token", required = false) UUID token){
        if (opaqueTokenProvider.isTokenValid(token)){
            System.out.println("user logged in with existing token: " + token);
            return ResponseEntity.ok(token.toString());
        }

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        password = encryptionService.hash(password);


        Account account = accountRepository.findByUsername(username);
        String actualPass = account.getPassword();
        if (password.equals(actualPass)){
            Token newToken = opaqueTokenProvider.generateToken(username);
            UUID tokenId = newToken.getTokenId();
            return ResponseEntity.ok(tokenId.toString());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication error");
    }

    @PostMapping("/register")
    public ResponseEntity<String> createAccount(@RequestBody SignupRequest signupRequest) {
        if (signupRequest.getPassword().isEmpty() || signupRequest.getUsername().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request body was invalid");
        } else if (authService.usernameTaken(signupRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username allready taken");
        } else {
            authService.createAndSaveUser(signupRequest);
            return ResponseEntity.ok("Account created");
        }
    }

    @GetMapping("/authenticateRequest")
    public ResponseEntity<Boolean> authenticateRequest(@RequestHeader("token") UUID token) {
        System.out.println("received request from token: " + token);
        if (opaqueTokenProvider.isTokenValid(token)) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
}
