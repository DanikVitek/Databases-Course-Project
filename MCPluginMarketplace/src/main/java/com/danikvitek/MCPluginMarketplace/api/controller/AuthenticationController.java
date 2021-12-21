package com.danikvitek.MCPluginMarketplace.api.controller;

import com.danikvitek.MCPluginMarketplace.api.dto.RegistrationDto;
import com.danikvitek.MCPluginMarketplace.api.dto.SimpleUserDto;
import com.danikvitek.MCPluginMarketplace.api.dto.jwt.JwtRequestDto;
import com.danikvitek.MCPluginMarketplace.api.dto.jwt.JwtResponseDto;
import com.danikvitek.MCPluginMarketplace.config.security.jwt.JwtProcessor;
import com.danikvitek.MCPluginMarketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor(onConstructor_ = { @Autowired })
public final class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProcessor jwtProcessor;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    @PostMapping("/login")
    public @NotNull ResponseEntity<JwtResponseDto> login(@Valid @RequestBody @NotNull JwtRequestDto jwtRequestDto) {
        String username = jwtRequestDto.getLogin();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        jwtRequestDto.getPassword()
                )
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String token = jwtProcessor.createJwt(
                username,
                userDetails.getAuthorities().stream().findFirst().get()
        );
        return ResponseEntity.ok(new JwtResponseDto(token));
    }

    @PostMapping("/signup")
    public @NotNull ResponseEntity<SimpleUserDto> signUp(@Valid @RequestBody RegistrationDto registrationDto) {
        SimpleUserDto userDto = userService.userToSimpleDto(userService.create(registrationDto));
        return ResponseEntity.ok(userDto);
    }
}
