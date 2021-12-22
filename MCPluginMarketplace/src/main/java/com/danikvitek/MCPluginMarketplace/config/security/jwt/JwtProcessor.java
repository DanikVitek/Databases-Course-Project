package com.danikvitek.MCPluginMarketplace.config.security.jwt;

import com.danikvitek.MCPluginMarketplace.config.security.UserPrincipal;
import com.danikvitek.MCPluginMarketplace.data.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class JwtProcessor {

    private final JwtProperties properties;
    private final String secret;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtProcessor(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
                        @NotNull JwtProperties properties) {
        this.userDetailsService = userDetailsService;
        this.properties = properties;
        this.secret = getBase64EncodedSecretKey(properties.getSecret());
    }

    private String getBase64EncodedSecretKey(@NotNull String secret) {
        return Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createJwt(String username, GrantedAuthority authority) {

        Claims claims = createClaims(username, authority);

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + properties.getExpirationMillis());

        return buildJwt(claims, now, expirationDate);
    }

    private @NotNull Claims createClaims(String username, @NotNull GrantedAuthority authority) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", authority.getAuthority());
        return claims;
    }

    private @NotNull @Unmodifiable Collection<? extends GrantedAuthority> getAuthorities(String jwt) {
        String role = (String) getClaims(jwt).getBody().get("role");
        return Set.of(new SimpleGrantedAuthority(role));
    }

    private String buildJwt(Claims claims, Date issuedAt, Date expirationDate) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, this.secret)
                .compact();
    }

    Authentication getAuthentication(String jwt) {
        UserDetails userDetails = new UserPrincipal(User.builder().username(getUsername(jwt)).build());
        return new UsernamePasswordAuthenticationToken(userDetails, "", getAuthorities(jwt));
    }

    private String getUsername(String jwt) {
        return getClaims(jwt).getBody().getSubject();
    }

    private Jws<Claims> getClaims(String jwt) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt);
    }

    String getJwt(@NotNull HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return "no jwt";
    }

    boolean isValid(String jwt) {
        return jwt != null && jwt.contains(".") && hasNoWhitespaces(jwt) && isNotExpired(jwt);
    }

    @Contract(pure = true)
    private boolean hasNoWhitespaces(@NotNull String token) {
        return !token.matches("[\\s]");
    }

    private boolean isNotExpired(String jwt) {
        return isExpirationNotBeforeNow(getClaims(jwt));
    }

    private boolean isExpirationNotBeforeNow(@NotNull Jws<Claims> claims) {
        return !claims.getBody().getExpiration().before(new Date());
    }
}
