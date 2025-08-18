package com.example.ForoHub.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenService {
    private final Algorithm algorithm;
    private final long expirationMillis;

    public TokenService(
            @Value("${JWT_SECRET}") String secret,
            @Value("${jwt.expiration-seconds:3600}") long expirationSeconds
    ) {
        this.algorithm = Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMillis = expirationSeconds * 1000L;
    }

    /** Genera un JWT con subject = email del usuario */
    public String generarToken(String subject) {
        var now = new Date();
        var exp = new Date(now.getTime() + expirationMillis);
        return JWT.create()
                .withSubject(subject)
                .withIssuedAt(now)
                .withExpiresAt(exp)
                .sign(algorithm);
    }

    /** Devuelve el subject (email) o lanza JWTVerificationException si es inválido/expirado */
    public String getSubject(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token).getSubject();
    }
}
