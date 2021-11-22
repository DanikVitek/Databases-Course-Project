package com.danikvitek.MCPluginMarketplace.controllers;

import com.danikvitek.MCPluginMarketplace.dto.JWTRequestDto;
import com.danikvitek.MCPluginMarketplace.dto.JWTResponseDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {
    @PostMapping("/login")
    public ResponseEntity<JWTResponseDto> login(@RequestBody JWTRequestDto jwtRequestDto) {

        JWTResponseDto jwtResponseDto = new JWTResponseDto();
        jwtResponseDto.setToken(getJWTToken(jwtRequestDto));

        return ResponseEntity.ok(jwtResponseDto);
    }

    private String getJWTToken(JWTRequestDto jwtRequestDto) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(jwtRequestDto.getLogin())
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}
