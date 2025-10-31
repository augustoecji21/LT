package com.litethinking.app.config;

import com.litethinking.app.auth.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthFilter jwtFilter;

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      // CORS + CSRF
      .cors(cors -> {})                          
      .csrf(csrf -> csrf.disable())

      // Stateless
      .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

      // Autorización
      .authorizeHttpRequests(auth -> auth
          // Preflight CORS
          .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

          // Público
          .requestMatchers(HttpMethod.GET,
              "/health",
              "/api/inventario/**",
              "/swagger-ui/**",
              "/v3/api-docs/**"
          ).permitAll()
          .requestMatchers("/auth/login").permitAll()

          // Lecturas generales
          .requestMatchers(HttpMethod.GET, "/api/**").permitAll()

          // Mutaciones de API solo ADMIN
          .requestMatchers("/api/**").hasRole("ADMIN")

          // Resto autenticado
          .anyRequest().authenticated()
      )

      // Filtro JWT
      .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  /** Orígenes/headers/métodos permitidos para el front (CORS). */
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration cfg = new CorsConfiguration();
    // Cambia si tu front corre en otro origen
    cfg.setAllowedOrigins(List.of("http://localhost:5173"));
    cfg.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
    cfg.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
    cfg.setAllowCredentials(true); // no es requerido para JWT, pero no estorba

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", cfg);
    return source;
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration c) throws Exception {
    return c.getAuthenticationManager();
  }
}
