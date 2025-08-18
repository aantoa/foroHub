package com.example.ForoHub.api.controller;

import com.example.ForoHub.domain.login.LoginRequest;
import com.example.ForoHub.domain.login.TokenResponse;
import com.example.ForoHub.infra.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated LoginRequest req) {
        try {
            var token = new UsernamePasswordAuthenticationToken(req.email(), req.password());
            var auth = authManager.authenticate(token); // lanza AuthenticationException si falla
            var principal = (UserDetails) auth.getPrincipal();
            var jwt = tokenService.generarToken(principal.getUsername());
            return ResponseEntity.ok(new TokenResponse(jwt, "Bearer"));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", ex.getClass().getSimpleName(), "message", "Credenciales inválidas"));
        }
    }
}
