package com.ProConnect.auth;

import com.ProConnect.config.ApplicationProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final ApplicationProperties applicationProperties;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        http.authorizeHttpRequests(customizer -> {
            customizer
                    .requestMatchers("/swagger-ui/**",
                            "/v3/api-docs/**",
                            "/swagger-resources/**",
                            "/swagger-ui.html",
                            "/webjars/**").permitAll()
                    .requestMatchers(antMatcher(HttpMethod.POST, "/api/users/add")).permitAll()
                    .requestMatchers("/api/debug/**").permitAll()
                    .requestMatchers("/jobrunr/**").permitAll()
                    .anyRequest().authenticated();
        });

        http.csrf(csrf -> csrf.disable()); //TODO: will implement later

        http.cors(customizer -> customizer.configurationSource(corsConfigurationSource));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            // IMPORTANT: fallback if allowedOrigins is null or empty
            List<String> allowedOrigins = applicationProperties.getAllowedOrigins();
            if (allowedOrigins == null || allowedOrigins.isEmpty()) {
                allowedOrigins = List.of("http://localhost:9090"); // fallback default origin for dev
            }
            config.setAllowedOrigins(allowedOrigins);
            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            config.setAllowCredentials(true);
            config.setAllowedHeaders(List.of("*"));

            return config;
        };
    }
}
