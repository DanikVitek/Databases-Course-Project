package com.danikvitek.MCPluginMarketplace.api.controller;

import com.danikvitek.MCPluginMarketplace.config.security.jwt.JwtProcessor;
import com.danikvitek.MCPluginMarketplace.util.exception.NoTokenHeaderException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

final class PackageObject {
    private static PackageObject instance;
    private PackageObject(){}
    public static PackageObject getInstance() {
        if (instance == null) instance = new PackageObject();
        return instance;
    }

    public boolean accountIsValid(@NotNull UserDetails userDetails) {
        return  userDetails.isAccountNonLocked() &&
                userDetails.isAccountNonExpired() &&
                userDetails.isEnabled() &&
                userDetails.isCredentialsNonExpired();
    }

    public UserDetails getUserDetails(@NotNull HttpHeaders headers,
                                      @NotNull JwtProcessor jwtProcessor,
                                      @NotNull UserDetailsService userDetailsService) {
        String token = Optional.ofNullable(headers.get("Authorization"))
                .orElseThrow(NoTokenHeaderException::new)
                .stream()
                .filter(s -> s.startsWith("Bearer "))
                .findFirst()
                .orElseThrow(NoTokenHeaderException::new)
                .replace("Bearer ", "");
        String username = jwtProcessor.getUsernameFromToken(token);
        return userDetailsService.loadUserByUsername(username);
    }
}