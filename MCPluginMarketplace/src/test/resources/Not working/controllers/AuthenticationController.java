package com.danikvitek.MCPluginMarketplace.controllers;

import com.danikvitek.MCPluginMarketplace.configuration.security.jwt.JwtProcessor;
import com.danikvitek.MCPluginMarketplace.dto.AccountDto;
import com.danikvitek.MCPluginMarketplace.dto.JwtRequestDto;
import com.danikvitek.MCPluginMarketplace.dto.JwtResponseDto;
import com.danikvitek.MCPluginMarketplace.dto.RegistrationDto;
import com.danikvitek.MCPluginMarketplace.model.User;
import com.danikvitek.MCPluginMarketplace.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;

@Controller
public class AuthenticationController {

    private static final User.Role DEFAULT_ROLE = User.Role.user;

    private final AuthenticationManager authenticationManager;
    private final JwtProcessor jwtProcessor;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtProcessor jwtProcessor,
                                    UserDetailsService userDetailsService,
                                    UserService userService,
                                    PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtProcessor = jwtProcessor;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponseDto> signIn(@RequestBody JwtRequestDto jwtRequestDto) {
        String username = jwtRequestDto.getLogin();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, jwtRequestDto.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String token = jwtProcessor.createJwt(
                username, (Collection<GrantedAuthority>) userDetails.getAuthorities()
        );
        return ResponseEntity.ok(new JwtResponseDto(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<AccountDto> signUp(@RequestBody RegistrationDto registrationDto) {
        User user = createAccount(registrationDto);
        return ResponseEntity.ok(createAccountDto(userService.createAccount(user)));
    }

    private AccountDto createAccountDto(User account) {
        return new AccountDto(account.getUsername());
    }

    private User createAccount(RegistrationDto registrationDto) {
        User user = User.builder()
                .username(registrationDto.getUsername())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .firstName(registrationDto.getFirstName())
                .lastName(registrationDto.getLastName())
                .email(registrationDto.getEmail())
                .registrationTime(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        user.setRole(DEFAULT_ROLE);
        return user;
    }
}