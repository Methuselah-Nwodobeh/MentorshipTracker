package com.guidetrack.mentorship_tracker.config;

import com.guidetrack.mentorship_tracker.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        //get authorization token from request header
        Optional<String> authHeader = Optional.ofNullable(request.getHeader("Authorization"));
        authHeader.filter(h -> h.startsWith("Bearer "))
                .map(h -> h.substring(7))
                .ifPresent(token -> {
                    Optional<String> usernameOptional = Optional.ofNullable(jwtService.extractUserName(token));
                    // get user details from database
                    usernameOptional.filter(username -> SecurityContextHolder.getContext().getAuthentication() == null)
                            .ifPresent(username -> {
                                UserDetails userDetails = userService.loadUserByUsername(username);
                                // check if token is valid
                                if (Boolean.TRUE.equals(jwtService.isTokenValid(token, userDetails))) {
                                    UsernamePasswordAuthenticationToken authenticationToken =
                                            new UsernamePasswordAuthenticationToken(userDetails,
                                                    null,
                                                    userDetails.getAuthorities());
                                    // enforce authentication token with details of request
                                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                                    // Update SecurityContextHolder or authentication token
                                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                                }
                            });
                });
        //add filter chain
        filterChain.doFilter(request, response);
    }
}
