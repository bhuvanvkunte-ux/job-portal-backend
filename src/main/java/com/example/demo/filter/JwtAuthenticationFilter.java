package com.example.demo.filter;

import com.example.demo.config.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getServletPath();

        System.out.println("Filter Path = " + path);

        return request.getMethod().equals("OPTIONS")
                || path.equals("/users/login")
                || path.equals("/users/register")
                || path.equals("/users/forgot-password")
                || path.equals("/users/verify-otp")
                || path.equals("/users/reset-password");
    }

    @Override

    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)

            throws ServletException, IOException {
        System.out.println("Request Path = " + request.getServletPath());


        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            if (jwtUtil.validateToken(token)) {

                String email = jwtUtil.extractEmail(token);
                String role = jwtUtil.extractRole(token);

                System.out.println("Email = " + email);
                System.out.println("Role = " + role);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + role))
                        );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);

                System.out.println("Authenticated User: " + email);

                System.out.println(
                        SecurityContextHolder.getContext()
                                .getAuthentication()
                                .getAuthorities()
                );
            }
        }

        filterChain.doFilter(request, response);
    }
}