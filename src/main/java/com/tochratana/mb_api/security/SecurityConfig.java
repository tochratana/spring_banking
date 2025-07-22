package com.tochratana.mb_api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final String ROLE_ADMIN = "ADMIN";
    private final String ROLE_STAFF = "STAFF";
    private final String ROLE_CUSTOMER = "CUSTOMER";

    @Bean
    public InMemoryUserDetailsManager configureUser(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("qwer"))
                .roles(ROLE_ADMIN)
                .build();
        manager.createUser(admin);

        UserDetails staff = User.builder()
                .username("staff")
                .password(passwordEncoder.encode("qwer"))
                .roles(ROLE_STAFF)
                .build();
        manager.createUser(staff);

        UserDetails customer = User.builder()
                .username("customer")
                .password(passwordEncoder.encode("qwer"))
                .roles(ROLE_CUSTOMER)
                .build();
        manager.createUser(customer);

        return manager;

    }

    // Decides which endpoints need login and which are open to everyone.
    @Bean
    public SecurityFilterChain configureApiSecurity(HttpSecurity http) throws Exception {
        // 1. Require authentication for all requests
        http.authorizeHttpRequests(endpoint -> endpoint
                // Can accesss for a role
                .requestMatchers(HttpMethod.POST, "/api/v1/customers/**").hasAnyRole(ROLE_ADMIN, ROLE_STAFF)
                .requestMatchers(HttpMethod.PUT, "/api/v1/customers/**").hasAnyRole(ROLE_ADMIN, ROLE_STAFF)
                .requestMatchers(HttpMethod.DELETE, "/api/v1/customers/**").hasAnyRole(ROLE_ADMIN)
                .requestMatchers(HttpMethod.GET, "/api/v1/customers/**").hasAnyRole(ROLE_ADMIN, ROLE_STAFF, ROLE_CUSTOMER)
                .requestMatchers("/api/v1/accounts/**").hasAnyRole(ROLE_ADMIN, ROLE_STAFF, ROLE_CUSTOMER)
                .anyRequest()
                .authenticated());

        // 2. Disable default form login UI
        http.formLogin(form -> form.disable());

        // 3. Use HTTP Basic authentication (for now, can be replaced with JWT later)
        http.httpBasic(Customizer.withDefaults());

        // 4. Disable CSRF protection (OK for stateless APIs)
        http.csrf(token -> token.disable());

        // 5. Ensure the app is stateless (no session will be created or used)
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }


}
