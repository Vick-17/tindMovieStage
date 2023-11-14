package com.tindMovie.tindMovie.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Filtre personnalisé pour l'autorisation des requêtes.
 * Ce filtre vérifie le jeton d'authentification dans l'en-tête de la requête
 * et effectue les opérations d'authentification et d'autorisation nécessaires.
 */
@Slf4j
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = null;

        // Vérifie si le chemin de la requête est "/login" ou "/refreshToken"
        // Ces chemins sont exclus de l'authentification
        if (request.getServletPath().equals("/login") || request.getServletPath().equals("/refreshToken")) {
            filterChain.doFilter(request, response); // Passe au filtre suivant
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    // Extrait le jeton d'authentification de l'en-tête
                    token = authorizationHeader.substring("Bearer ".length());

                    // Analyse et valide le jeton d'authentification
                    UsernamePasswordAuthenticationToken authenticationToken = jwtUtils.parseToken(token);

                    // Définit l'authentification de l'utilisateur dans le contexte de sécurité
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    filterChain.doFilter(request, response); // Passe au filtre suivant
                } catch (Exception e) {
                    log.error(String.format("Error auth token: %s", token), e);

                    // En cas d'erreur lors de la validation du jeton d'authentification
                    response.setStatus(FORBIDDEN.value());
                    Map<String, String> error = new HashMap<>();
                    error.put("errorMessage", e.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            } else {
                filterChain.doFilter(request, response); // Passe au filtre suivant
            }
        }
    }
}
