package com.clothesdelivery.web.security;

import com.clothesdelivery.web.enums.Role;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final String[] _allowedUrls = new String[] {
            "/login", "/signup",
            "/terms", "/", "/about", "/contact", "/error", "/notfound",
            "/css/**",  "/fonts/**", "/js/**", "/images/**", "/libs/**",
            "/products", "/detail/**", "/admin/**", "/upload"
    };

    private final CustomUserDetailsService _userDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        _userDetailsService = userDetailsService;
    }

    @Contract(" -> new")
    @Bean
    public static @NotNull PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(@NotNull HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                    .invalidSessionUrl("/login")
                    .maximumSessions(1)
                    .expiredUrl("/login").and().and()
                .authorizeHttpRequests(authorize -> {
                    try {
                        authorize
                            .requestMatchers(_allowedUrls).permitAll()
                                // todo: configure restrictions in /admin/**
                            //.requestMatchers("/admin/**").hasRole(String.valueOf(Role.Customer))
                            .anyRequest().authenticated();
                        authorize
                            .and()
                            .exceptionHandling()
                            .accessDeniedPage("/forbidden");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .formLogin(form -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
                    .loginProcessingUrl("/login")
                    .permitAll())
                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login?logout")
                    .invalidateHttpSession(true)
                    .permitAll());

        return httpSecurity.build();
    }

    public void configure(@NotNull AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(_userDetailsService).passwordEncoder(passwordEncoder());
    }
}
