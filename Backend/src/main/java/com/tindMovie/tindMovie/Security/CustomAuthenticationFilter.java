package com.tindMovie.tindMovie.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.rmi.ServerException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public static final String BAD_CREDENTIAL_MESSAGE = "Authentication failed for username: %s and password: %s";

    private final AuthenticationManager authenticationManager;

    @Autowired
    private final JwtUtils jwtUtils;

    /**
     * Tente d'effectuer l'authentification de l'utilisateur en utilisant le nom d'utilisateur et le mot de passe fournis.
     *
     * @param request  HttpServletRequest contenant les informations d'identification de l'utilisateur
     * @param response HttpServletResponse utilisé pour envoyer la réponse d'authentification
     * @return L'objet Authentication si l'authentification est réussie
     * @throws AuthenticationException si l'authentification échoue
     */
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String email = null;
        String password = null;
        try {
            // Lecture des données JSON de la requête et récupération de l'email et du mot de passe
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> map = objectMapper.readValue(request.getInputStream(), Map.class);
            email = map.get("email");
            password = map.get("password");
            System.out.println(email + password);
            log.debug("Login with email: {}", email);

            // Authentification de l'utilisateur avec l'email et le mot de passe fournis
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (AuthenticationException e) {
            log.error(String.format(BAD_CREDENTIAL_MESSAGE, email, password), e);
            throw e;
        } catch (Exception e) {
            // Gestion des erreurs lors de l'authentification
            response.setStatus(INTERNAL_SERVER_ERROR.value());
            Map<String, String> error = new HashMap<>();
            error.put("errorMessage", e.getMessage());
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), error);
            throw new RuntimeException(String.format("Error in attemptAuthentication with email %s and password %s", email, password), e);
        }
    }

    /**
     * Appelé lorsque l'authentification est réussie. Génère les jetons d'accès (access token) et de rafraîchissement (refresh token)
     * et les ajoute comme en-têtes de réponse.
     *
     * @param request        HttpServletRequest contenant les informations d'authentification
     * @param response       HttpServletResponse utilisé pour envoyer la réponse
     * @param chain          FilterChain utilisé pour passer la demande à travers d'autres filtres
     * @param authentication L'objet Authentication contenant les informations d'authentification de l'utilisateur
     * @throws IOException     en cas d'erreur lors de l'écriture de la réponse
     * @throws ServerException si une erreur de serveur se produit
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServerException {
        // Récupération des informations de l'utilisateur authentifié
        User user = (User) authentication.getPrincipal();

        // Création du jeton d'accès (access token) et du jeton de rafraîchissement (refresh token)
        String accesToken = jwtUtils.createAccessToken(user.getUsername(), request.getRequestURL().toString(),
                user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        String refreshToken = jwtUtils.createRefreshToken(user.getUsername());
        // Ajout des en-têtes de réponse avec les jetons
        response.addHeader("access_token", accesToken);
        response.addHeader("refresh_token", refreshToken);

        //Construction du body de la response serveur
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> role = new HashMap<>();
        Collection<GrantedAuthority> authorities = user.getAuthorities();
        if (!authorities.isEmpty()) {
            String autho = authorities.iterator().next().getAuthority();
            role.put("role", autho);
        }
        response.setContentType(APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getOutputStream(), role);


    }

    /**
     * Appelé lorsque l'authentification échoue. Définit le statut de la réponse comme non autorisé (401) et renvoie un message d'erreur JSON.
     *
     * @param request  HttpServletRequest contenant les informations d'authentification
     * @param response HttpServletResponse utilisé pour envoyer la réponse
     * @param failed   L'exception d'authentification ayant échoué
     * @throws IOException     en cas d'erreur lors de l'écriture de la réponse
     * @throws ServerException si une erreur de serveur se produit
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServerException {
        // Gestion de l'authentification échouée
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> error = new HashMap<>();
        error.put("errorMessage", "Bad credential");
        response.setContentType(APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getOutputStream(), error);
    }

}
