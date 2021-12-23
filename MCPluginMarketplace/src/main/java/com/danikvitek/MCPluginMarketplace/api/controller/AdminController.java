package com.danikvitek.MCPluginMarketplace.api.controller;

import com.danikvitek.MCPluginMarketplace.api.dto.BanDto;
import com.danikvitek.MCPluginMarketplace.api.dto.CategoryDto;
import com.danikvitek.MCPluginMarketplace.config.security.UserDetailsImpl;
import com.danikvitek.MCPluginMarketplace.config.security.jwt.JwtProcessor;
import com.danikvitek.MCPluginMarketplace.data.model.entity.BannedUser;
import com.danikvitek.MCPluginMarketplace.data.model.entity.Comment;
import com.danikvitek.MCPluginMarketplace.data.model.entity.Role;
import com.danikvitek.MCPluginMarketplace.data.model.entity.User;
import com.danikvitek.MCPluginMarketplace.service.BannedUserService;
import com.danikvitek.MCPluginMarketplace.service.CategoryService;
import com.danikvitek.MCPluginMarketplace.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public final class AdminController {
    private final UserDetailsService userDetailsService;
    private final JwtProcessor jwtProcessor;
    private final BannedUserService bannedUserService;
    private final CategoryService categoryService;
    private final CommentService commentService;

    @GetMapping("/users/bans/{id}")
    public @NotNull ResponseEntity<BanDto> showBannedUser(@PathVariable long id,
                                                          @RequestHeader HttpHeaders headers) {
        UserDetailsImpl userDetails = (UserDetailsImpl) PackageObject.getInstance()
                .getUserDetails(headers, jwtProcessor, userDetailsService);
        if (PackageObject.getInstance().accountIsValid(userDetails) &&
            (userDetails.getUser().getRole() == Role.admin || userDetails.getUser().getRole() == Role.moderator)) {
            BannedUser bannedUser = bannedUserService.fetchById(id);
            BanDto banDto = BanDto.builder()
                    .userId(id)
                    .reason(bannedUser.getReason())
                    .build();
            return ResponseEntity.ok(banDto);
        } else throw new AuthenticationException("Illegal access") {};
    }

    @PostMapping("/users/bans")
    public @NotNull ResponseEntity<Void> banUser(@Valid @RequestBody BanDto banDto,
                                                 @RequestHeader HttpHeaders headers) {
        UserDetailsImpl userDetails = (UserDetailsImpl) PackageObject.getInstance()
                .getUserDetails(headers, jwtProcessor, userDetailsService);
        if (PackageObject.getInstance().accountIsValid(userDetails) &&
            userDetails.getUser().getRole() == Role.admin) {
            long bannedUserId = bannedUserService.create(banDto.getUserId(), banDto.getReason()).getUserId();
            String location = String.format("/users/bans/%d", bannedUserId);
            return ResponseEntity.created(URI.create(location)).build();
        } else throw new AuthenticationException("Illegal access") {};
    }

    @PatchMapping("/users/bans")
    public @NotNull ResponseEntity<Void> updateBan(@Valid @RequestBody BanDto banDto,
                                                   @RequestHeader HttpHeaders headers) {
        UserDetailsImpl userDetails = (UserDetailsImpl) PackageObject.getInstance()
                .getUserDetails(headers, jwtProcessor, userDetailsService);
        if (PackageObject.getInstance().accountIsValid(userDetails) &&
            userDetails.getUser().getRole() == Role.admin) {
            bannedUserService.update(banDto.getUserId(), banDto.getReason());
            String location = String.format("/users/bans/%d", banDto.getUserId());
            return ResponseEntity.created(URI.create(location)).build();
        } else throw new AuthenticationException("Illegal access") {};
    }

    @DeleteMapping("/users/bans/{id}")
    public @NotNull ResponseEntity<Void> unbanUser(@PathVariable long id,
                                                   @RequestHeader HttpHeaders headers) {
        UserDetailsImpl userDetails = (UserDetailsImpl) PackageObject.getInstance()
                .getUserDetails(headers, jwtProcessor, userDetailsService);
        if (PackageObject.getInstance().accountIsValid(userDetails) &&
            userDetails.getUser().getRole() == Role.admin) {
            bannedUserService.delete(id);
            return ResponseEntity.noContent().build();
        } else throw new AuthenticationException("Illegal access") {};
    }

    @PostMapping("/categories")
    public @NotNull ResponseEntity<Void> createCategory(@Valid @RequestBody @NotNull CategoryDto categoryDto,
                                                @RequestHeader HttpHeaders headers) {
        UserDetailsImpl userDetails = (UserDetailsImpl) PackageObject.getInstance()
                .getUserDetails(headers, jwtProcessor, userDetailsService);
        if (PackageObject.getInstance().accountIsValid(userDetails) && userDetails.getUser().getRole() == Role.admin) {
            short id = categoryService.create(categoryDto.getTitle()).getId();
            String location = String.format("/categories/%d", id);
            return ResponseEntity.created(URI.create(location)).build();
        } else throw new AuthenticationException("Invalid access") {};
    }

    @PatchMapping("/categories/{id}")
    public @NotNull ResponseEntity<Void> updateCategory(@PathVariable short id,
                                                        @Valid @RequestBody @NotNull CategoryDto categoryDto,
                                                        @RequestHeader HttpHeaders headers) {
        UserDetailsImpl userDetails = (UserDetailsImpl) PackageObject.getInstance()
                .getUserDetails(headers, jwtProcessor, userDetailsService);
        if (PackageObject.getInstance().accountIsValid(userDetails) && userDetails.getUser().getRole() == Role.admin) {
            categoryService.update(id, categoryDto.getTitle());
            String location = String.format("/categories/%d", id);
            return ResponseEntity.created(URI.create(location)).build();
        } else throw new AuthenticationException("Invalid access") {};
    }

    @DeleteMapping("/categories/{id}")
    public @NotNull ResponseEntity<Void> deleteCategory(@PathVariable short id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/comments/{id}")
    public @NotNull ResponseEntity<Void> delete(@PathVariable long id,
                                                @RequestHeader @NotNull HttpHeaders headers) {
        UserDetailsImpl userDetails = (UserDetailsImpl) PackageObject.getInstance()
                .getUserDetails(headers, jwtProcessor, userDetailsService);
        User user = userDetails.getUser();
        if (PackageObject.getInstance().accountIsValid(userDetails) && user.getRole() == Role.admin) {
            commentService.delete(id);
            return ResponseEntity.noContent().build();
        } else throw new AuthenticationException("Invalid access") {};
    }
}
