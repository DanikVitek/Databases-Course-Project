package com.danikvitek.MCPluginMarketplace.api.controller;

import com.danikvitek.MCPluginMarketplace.api.dto.CommentDto;
import com.danikvitek.MCPluginMarketplace.config.security.UserDetailsImpl;
import com.danikvitek.MCPluginMarketplace.config.security.jwt.JwtProcessor;
import com.danikvitek.MCPluginMarketplace.data.model.entity.Comment;
import com.danikvitek.MCPluginMarketplace.data.model.entity.Role;
import com.danikvitek.MCPluginMarketplace.data.model.entity.User;
import com.danikvitek.MCPluginMarketplace.service.CommentService;
import com.danikvitek.MCPluginMarketplace.service.PluginService;
import com.danikvitek.MCPluginMarketplace.service.UserService;
import com.danikvitek.MCPluginMarketplace.util.exception.PluginNotFoundException;
import com.danikvitek.MCPluginMarketplace.util.exception.UserNotFoundException;
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
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public final class CommentController {
    private final CommentService commentService;
    private final UserDetailsService userDetailsService;
    private final JwtProcessor jwtProcessor;

    @GetMapping("/{id}")
    public @NotNull ResponseEntity<CommentDto> show(@PathVariable long id,
                                                    @RequestHeader @NotNull HttpHeaders headers) {
        UserDetailsImpl userDetails = (UserDetailsImpl) PackageObject.getInstance()
                .getUserDetails(headers, jwtProcessor, userDetailsService);
        if (PackageObject.getInstance().accountIsValid(userDetails) &&
                (userDetails.getUser().getRole() == Role.moderator ||
                 userDetails.getUser().getRole() == Role.admin)
        ) {
            CommentDto commentDto = commentToDto(commentService.fetchById(id));
            return ResponseEntity.ok(commentDto);
        } else throw new AuthenticationException("Invalid access") {};
    }

    @PostMapping
    public @NotNull ResponseEntity<Void> create(@Valid @RequestBody CommentDto commentDto,
                                                @RequestHeader @NotNull HttpHeaders headers) {
        UserDetailsImpl userDetails = (UserDetailsImpl) PackageObject.getInstance()
                .getUserDetails(headers, jwtProcessor, userDetailsService);
        if (PackageObject.getInstance().accountIsValid(userDetails)) {
            long commentId = commentService.create(
                    commentDto.getPluginId(), commentDto.getUserId(), commentDto.getContent()
            ).getId();
            String location = String.format("/comments/%d", commentId);
            return ResponseEntity.created(URI.create(location)).build();
        } else throw new AuthenticationException("Invalid access") {};
    }
    
    @PostMapping("/{id}")
    public @NotNull ResponseEntity<Void> respond(@PathVariable long id,
                                                 @Valid @RequestBody CommentDto commentDto,
                                                 @RequestHeader @NotNull HttpHeaders headers) {
        UserDetailsImpl userDetails = (UserDetailsImpl) PackageObject.getInstance()
                .getUserDetails(headers, jwtProcessor, userDetailsService);
        if (PackageObject.getInstance().accountIsValid(userDetails)) {
            long commentId = commentService.respond(
                    id,
                    commentDto.getPluginId(), commentDto.getUserId(), commentDto.getContent()
            ).getId();
            String location = String.format("/comments/%d", commentId);
            return ResponseEntity.created(URI.create(location)).build();
        } else throw new AuthenticationException("Invalid access") {};
    }

    @PatchMapping("/{id}")
    public @NotNull ResponseEntity<CommentDto> update(@PathVariable long id,
                                                      @Valid @RequestBody CommentDto commentDto,
                                                      @RequestHeader @NotNull HttpHeaders headers) {
        UserDetailsImpl userDetails = (UserDetailsImpl) PackageObject.getInstance()
                .getUserDetails(headers, jwtProcessor, userDetailsService);
        if (PackageObject.getInstance().accountIsValid(userDetails)) {
            Comment comment = commentService.fetchById(id);
            if (Objects.equals(comment.getUserId(), userDetails.getUser().getId())) {
                CommentDto updatedComment = commentToDto(commentService.update(id, commentDto.getContent()));
                return ResponseEntity.ok(updatedComment);
            } else throw new AuthenticationException("Invalid access") {};
        } else throw new AuthenticationException("Invalid access") {};
    }

    @DeleteMapping("/comments/{id}")
    public @NotNull ResponseEntity<Void> delete(@PathVariable long id,
                                                @RequestHeader @NotNull HttpHeaders headers) {
        UserDetailsImpl userDetails = (UserDetailsImpl) PackageObject.getInstance()
                .getUserDetails(headers, jwtProcessor, userDetailsService);
        Comment comment = commentService.fetchById(id);
        User user = userDetails.getUser();
        if (PackageObject.getInstance().accountIsValid(userDetails) && Objects.equals(comment.getUserId(), user.getId())) {
            commentService.delete(id);
            return ResponseEntity.noContent().build();
        } else throw new AuthenticationException("Invalid access") {};
    }

    public CommentDto commentToDto(@NotNull Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .userId(comment.getUserId())
                .pluginId(comment.getPluginId())
                .content(comment.getContent())
                .publicationTime(comment.getPublicationTime())
                .responses(commentService.fetchResponsesById(comment.getId()).stream()
                        .map(this::commentToDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
