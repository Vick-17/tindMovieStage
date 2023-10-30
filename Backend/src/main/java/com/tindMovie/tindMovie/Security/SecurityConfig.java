package com.tindMovie.tindMovie.Security;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public AuthenticationManager authenticationManager(
    AuthenticationConfiguration authConfig
  ) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(
    HttpSecurity http,
    AuthenticationManager authenticationManager
  ) throws Exception {
    http
      .csrf(AbstractHttpConfigurer::disable)
      .cors(cors -> cors.configurationSource(corsConfigurationSource()))
      .sessionManagement(sess ->
        sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .authorizeHttpRequests(authz ->
        authz
          .requestMatchers(HttpMethod.GET, "/users/**")
          .permitAll()
          .requestMatchers(HttpMethod.GET, "/movie/**")
          .permitAll()
          .requestMatchers(HttpMethod.GET, "/realisator/**")
          .permitAll()
          .requestMatchers(HttpMethod.GET, "/actors/**")
          .permitAll()
          .requestMatchers(HttpMethod.POST, "/login/**")
          .permitAll()
          .requestMatchers(HttpMethod.POST, "/users/**")
          .permitAll()
          .requestMatchers(HttpMethod.POST, "/swipe/**")
          .permitAll()
          .requestMatchers(HttpMethod.DELETE, "/swipe/**")
          .permitAll()
          .requestMatchers(HttpMethod.PUT, "/swipe/**")
          .permitAll()
          .requestMatchers(HttpMethod.GET, "/swipe/**")
          .permitAll()
          .requestMatchers(HttpMethod.PUT, "/users/**")
          .permitAll()
          .requestMatchers(HttpMethod.POST, "/note/**")
          .permitAll()
          .requestMatchers(HttpMethod.GET, "/note/**")
          .permitAll()
          .requestMatchers(HttpMethod.POST, "/comment/**")
          .permitAll()
          .requestMatchers(HttpMethod.GET, "/comment/**")
          .permitAll()
          .requestMatchers(HttpMethod.POST, "/chat/**")
          .permitAll()
          .requestMatchers(HttpMethod.GET, "/chat/**")
          .permitAll()
          .requestMatchers(HttpMethod.GET, "/app/**")
          .permitAll()
          .requestMatchers(HttpMethod.GET, "/send/**")
          .permitAll()
          .requestMatchers(HttpMethod.GET, "/ws/**")
          .permitAll()
          .requestMatchers(HttpMethod.POST, "/ws/**")
          .permitAll()
          .anyRequest()
          .authenticated()
      )
      .addFilter(new CustomAuthenticationFilter(authenticationManager))
      .addFilterBefore(
        new CustomAuthorizationFilter(),
        UsernamePasswordAuthenticationFilter.class
      )
      .headers(headers -> headers.cacheControl(Customizer.withDefaults()));
    return http.build();
  }

  /**
   * Configuration source for CORS (Cross-Origin Resource Sharing).
   * Defines the CORS rules for allowing cross-origin requests.
   *
   * @return The CORS configuration source.
   */
  private CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOriginPatterns(Arrays.asList("*"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("*"));
    configuration.setExposedHeaders(Arrays.asList("access_token"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }
}
