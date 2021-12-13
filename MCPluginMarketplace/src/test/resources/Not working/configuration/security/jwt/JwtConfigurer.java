package com.danikvitek.MCPluginMarketplace.configuration.security.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtProcessor jwtProcessor;

    public JwtConfigurer(JwtProcessor jwtProcessor) {
        this.jwtProcessor = jwtProcessor;
    }
    
    @Override
    public void configure(HttpSecurity builder) {
        JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(jwtProcessor);
        builder.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
