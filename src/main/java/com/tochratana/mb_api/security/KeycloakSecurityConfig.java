package com.tochratana.mb_api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class KeycloakSecurityConfig {

    private final String ROLE_ADMIN = "ADMIN";
    private final String ROLE_STAFF = "STAFF";
    private final String ROLE_CUSTOMER = "CUSTOMER";

    @Bean
    public SecurityFilterChain configureApiSecurity(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(endpoint -> endpoint
                // Customer endpoints
                .requestMatchers(HttpMethod.POST, "/api/v1/customers/**").hasAnyRole(ROLE_ADMIN, ROLE_STAFF)
                .requestMatchers(HttpMethod.PUT, "/api/v1/customers/**").hasAnyRole(ROLE_ADMIN, ROLE_STAFF)
                .requestMatchers(HttpMethod.DELETE, "/api/v1/customers/**").hasAnyRole(ROLE_ADMIN)
                .requestMatchers(HttpMethod.GET, "/api/v1/customers/**").hasAnyRole(ROLE_ADMIN, ROLE_STAFF, ROLE_CUSTOMER)

                // Account endpoints
                .requestMatchers("/api/v1/accounts/**").hasAnyRole(ROLE_ADMIN, ROLE_STAFF, ROLE_CUSTOMER)

                // Media API endpoints - allow all for testing and browser access
                .requestMatchers("/api/v1/medias/**").permitAll()

                // Static media files served by MediaConfig (direct file access)
                .requestMatchers("/media/**").permitAll()

                // All other requests require authentication
                .anyRequest().authenticated());

        http.formLogin(form -> form.disable());
        http.csrf(token -> token.disable());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Integrate custom JWT converter for Keycloak roles
        http.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverterForKeycloak()))
        );

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverterForKeycloak() {
        Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = jwt -> {
            Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
            Collection<String> roles = realmAccess != null ? realmAccess.get("roles") : null;

            if (roles == null) {
                return java.util.Collections.emptyList();
            }

            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        };

        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtConverter;
    }
}