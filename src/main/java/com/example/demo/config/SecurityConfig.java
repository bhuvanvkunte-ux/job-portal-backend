package com.example.demo.config;

import com.example.demo.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        System.out.println("SecurityConfig Loaded");

        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/users/register",
                                "/users/login",
                                "/users/forgot-password",
                                "/users/verify-otp",
                                "/users/reset-password",
                                "/public/**",
                                "/test-email",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        .requestMatchers("/jobs/**")
                        .authenticated()

                        .requestMatchers("/dashboard")
                        .hasRole("RECRUITER")

                        .requestMatchers("/users/search-candidates")
                        .hasRole("RECRUITER")

                        .requestMatchers("/applications/apply")
                        .hasRole("CANDIDATE")

                        .requestMatchers("/applications/candidate/**")
                        .hasRole("CANDIDATE")

                        .requestMatchers("/users/profile/**")
                        .hasRole("CANDIDATE")

                        .requestMatchers("/users/upload-resume/**")
                        .hasRole("CANDIDATE")

                        .requestMatchers("/users/resume/by-email/**").hasRole("RECRUITER")

                        .requestMatchers("/applications")
                        .hasRole("RECRUITER")

                        .requestMatchers("/applications/job/**")
                        .hasRole("RECRUITER")

                        .requestMatchers("/interviews")
                        .hasRole("RECRUITER")

                        .requestMatchers("/interviews/candidate/**")
                        .hasRole("CANDIDATE")

                        .requestMatchers("/interviews/*/status").hasRole("RECRUITER")

                        .requestMatchers("/saved-jobs/**")
                        .hasRole("CANDIDATE")

                        .requestMatchers("/applications/*/status")
                        .hasRole("RECRUITER")

                        .requestMatchers("/jobs/recommend")
                        .hasRole("CANDIDATE")

                        .requestMatchers("/recruiter-profile/**")
                        .hasRole("RECRUITER")

                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        .anyRequest()
                        .authenticated()
                )
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(
                List.of("http://localhost:5173", "http://localhost:3000", "https://*.vercel.app")
        );

        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }
}