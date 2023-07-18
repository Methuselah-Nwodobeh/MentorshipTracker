package com.guidetrack.mentorship_tracker.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.guidetrack.mentorship_tracker.utils.constants.SecurityConstants.ADMIN_ROLE;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers("/swagger-ui/**", "/v3/api-docs/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST,"api/v1/user").permitAll()
                        .requestMatchers("api/v1/admin/**").hasRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.POST,"api/v1/role/**").hasRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.PUT,"api/v1/role/**").hasRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.POST,"/api/v1/permission/**").hasRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.PUT,"/api/v1/permission/**").hasRole(ADMIN_ROLE)
                        .anyRequest()
                        .authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}