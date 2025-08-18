package com.example.ForoHub.infra.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final TokenService jwtUtil;
    private final AuthUserDetailsService uds;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        var header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            var token = header.substring(7);
            try {
                var username = jwtUtil.getSubject(token);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    var userDetails = uds.loadUserByUsername(username);
                    var auth = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (JWTVerificationException ex) {
                // Token inválido/expirado → dejamos que falle como 401 luego
            }
        }
        chain.doFilter(request, response);
    }
}
