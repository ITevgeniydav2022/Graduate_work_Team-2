package com.example.graduate_work_team2.checkConfig;

import ch.qos.logback.classic.spi.ILoggingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Slf4j
@Configuration
public class WebSecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/webjars/**",
            "/login", "/register"
    };

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user@gmail.com")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)  {
        try {
            http
                    .csrf().disable()
                    .authorizeHttpRequests((authz) ->
                            authz
                                    .mvcMatchers(AUTH_WHITELIST).permitAll()
                                    .mvcMatchers("/ads/**", "/users/**").authenticated()
                    )
                    .cors().disable()
                    .httpBasic(withDefaults());
        } catch (Exception e) {
            log.error("Error occurred " + e.getMessage());
            throw new RuntimeException(e);
        }
        try {
            return http.build();
        } catch (Exception e) {
            log.error("Error occurred " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}