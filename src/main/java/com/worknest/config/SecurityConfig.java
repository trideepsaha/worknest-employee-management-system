package com.worknest.config;

import com.worknest.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {

        // Temporary for Day 3 and Day 4 testing
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider =
                new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(customUserDetailsService);

        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http

                .csrf(csrf -> csrf.disable())

                .authenticationProvider(authenticationProvider())

                .authorizeHttpRequests(auth -> auth

                        // Public URLs
                        .requestMatchers(
                                "/",
                                "/login",
                                "/css/**",
                                "/js/**"
                        ).permitAll()

                        // TEMPORARY: Allow employee APIs for testing
                        .requestMatchers("/employees/**").permitAll()

                        // Role based access
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        .requestMatchers("/hr/**").hasRole("HR")

                        .requestMatchers("/manager/**").hasRole("MANAGER")

                        .requestMatchers("/employee/**").hasRole("EMPLOYEE")

                        // Any other request needs authentication
                        .anyRequest().authenticated()
                )

                .formLogin(login -> login

                        .loginPage("/login")

                        .loginProcessingUrl("/login")

                        .defaultSuccessUrl("/redirect", true)

                        .permitAll()
                )

                .logout(logout -> logout

                        .logoutUrl("/logout")

                        .logoutSuccessUrl("/login?logout")

                        .permitAll()
                );

        return http.build();
    }
}