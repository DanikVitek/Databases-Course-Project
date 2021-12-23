package com.danikvitek.MCPluginMarketplace.api.controller;

import com.danikvitek.MCPluginMarketplace.config.security.UserDetailsImpl;
import com.danikvitek.MCPluginMarketplace.config.security.jwt.JwtProcessor;
import com.danikvitek.MCPluginMarketplace.data.model.entity.Role;
import com.danikvitek.MCPluginMarketplace.data.model.entity.User;
import com.danikvitek.MCPluginMarketplace.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/moderator")
public final class ModeratorController {
    private final UserDetailsService userDetailsService;
    private final JwtProcessor jwtProcessor;
    private final CommentService commentService;

    @DeleteMapping("/comments/{id}")
    public @NotNull ResponseEntity<Void> delete(@PathVariable long id,
                                                @RequestHeader @NotNull HttpHeaders headers) {
        UserDetailsImpl userDetails = (UserDetailsImpl) PackageObject.getInstance()
                .getUserDetails(headers, jwtProcessor, userDetailsService);
        User user = userDetails.getUser();
        if (PackageObject.getInstance().accountIsValid(userDetails) && user.getRole() == Role.moderator) {
            commentService.delete(id);
            return ResponseEntity.noContent().build();
        } else throw new AuthenticationException("Invalid access") {};
    }
}
