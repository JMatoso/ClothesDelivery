package com.clothesdelivery.web.security;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final String[] _allowedUrls = new String[] {
            "/login", "/signup",
            "/terms", "/", "/about", "/contact", "/error", "/notfound",
            "/css/**",  "/fonts/**", "/js/**", "/images/**", "/libs/**",
            "/products", "/detail/**"
    };

    @Bean
    public SecurityFilterChain filterChain(@NotNull HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(_allowedUrls)
                .permitAll()
                .and()
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .loginProcessingUrl("/login")
                        .permitAll())
                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll());

        return httpSecurity.build();
    }
}
