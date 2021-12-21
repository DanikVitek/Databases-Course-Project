package com.danikvitek.MCPluginMarketplace.config.security.jwt;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtProcessor jwtProcessor;

    @Override
    public void configure(@NotNull HttpSecurity httpSecurity) {
        httpSecurity.addFilterBefore(
                new JwtAuthorizationFilter(jwtProcessor),
                UsernamePasswordAuthenticationFilter.class
        );
    }
}
