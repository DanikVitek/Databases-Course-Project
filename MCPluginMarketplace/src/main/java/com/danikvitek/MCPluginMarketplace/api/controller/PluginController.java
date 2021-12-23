package com.danikvitek.MCPluginMarketplace.api.controller;

import com.danikvitek.MCPluginMarketplace.api.dto.CommentDto;
import com.danikvitek.MCPluginMarketplace.api.dto.PluginDto;
import com.danikvitek.MCPluginMarketplace.api.dto.SimpleUserDto;
import com.danikvitek.MCPluginMarketplace.config.security.UserDetailsImpl;
import com.danikvitek.MCPluginMarketplace.config.security.jwt.JwtProcessor;
import com.danikvitek.MCPluginMarketplace.data.model.entity.Comment;
import com.danikvitek.MCPluginMarketplace.data.model.entity.Plugin;
import com.danikvitek.MCPluginMarketplace.data.model.entity.User;
import com.danikvitek.MCPluginMarketplace.service.CommentService;
import com.danikvitek.MCPluginMarketplace.service.PluginService;
import com.danikvitek.MCPluginMarketplace.service.UserService;
import com.danikvitek.MCPluginMarketplace.util.exception.PluginNotFoundException;
import com.danikvitek.MCPluginMarketplace.util.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/plugins")
public final class PluginController {
    private final PluginService pluginService;
    private final UserService userService;
    private final CommentService commentService;
    private final JwtProcessor jwtProcessor;
    private final UserDetailsService userDetailsService;

    @GetMapping
    public @NotNull ResponseEntity<List<PluginDto>> index(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "5") int size) {
        if (size <= 20) {
            List<PluginDto> plugins = pluginService
                    .fetchAll(page, size)
                    .map(pluginService::pluginToDto)
                    .getContent();
            return ResponseEntity.ok(plugins);
        } else throw new IllegalArgumentException("Page size must not be greater than twenty!");
    }

    @GetMapping("/{id}")
    public @NotNull ResponseEntity<PluginDto> show(@PathVariable long id) {
        PluginDto pluginDto = pluginService.pluginToDto(pluginService.fetchById(id));
        return ResponseEntity.ok(pluginDto);
    }

    @GetMapping("/{id}/authors")
    public @NotNull ResponseEntity<Set<SimpleUserDto>> showAuthors(@PathVariable long id) {
        Set<SimpleUserDto> authors = userService.fetchAuthorsByPluginId(id)
                .stream()
                .map(userService::userToSimpleDto)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}/comments")
    public @NotNull ResponseEntity<Collection<CommentDto>> showComments(@PathVariable long id) {
        Collection<CommentDto> comments = commentService.fetchByPluginId(id).stream()
                .map(this::commentToDto)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(comments);
    }
    
    @PostMapping
    public @NotNull ResponseEntity<Void> create(@Valid @RequestBody PluginDto pluginDto,
                                                @RequestHeader HttpHeaders headers) {
        UserDetailsImpl userDetails = (UserDetailsImpl) PackageObject.getInstance()
                .getUserDetails(headers, jwtProcessor, userDetailsService);
        if (PackageObject.getInstance().accountIsValid(userDetails)) {
            User firstAuthor = userDetails.getUser();
            Plugin plugin = pluginService.create(pluginDto, firstAuthor);
            String location = String.format("/plugins/%d", plugin.getId());
            return ResponseEntity.created(URI.create(location)).build();
        } else throw new AuthenticationException("Invalid access") {};
    }

    @PatchMapping("/{id}")
    public @NotNull ResponseEntity<Void> update(@PathVariable long id,
                                                @Valid @RequestBody PluginDto pluginDto) {
        pluginService.update(id, pluginDto);
        String location = String.format("/plugins/%d", id);
        return ResponseEntity.created(URI.create(location)).build();
    }
    
    @DeleteMapping("/{id}")
    public @NotNull ResponseEntity<Void> delete(@PathVariable long id) {
        pluginService.delete(id);
        return ResponseEntity.noContent().build();
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

    public Comment dtoToComment(CommentDto dto) {
        return dtoToComment(dto, false, false);
    }

    public Comment dtoToComment(@NotNull CommentDto dto, boolean includeId, boolean includeTime)
            throws UserNotFoundException, PluginNotFoundException {
        Comment.CommentBuilder builder = Comment.builder();
        if (includeId) builder = builder.id(dto.getId());
        if (includeTime) builder = builder.publicationTime(dto.getPublicationTime());
        return builder
                .userId(userService.fetchById(dto.getUserId()).getId())
                .pluginId(pluginService.fetchById(dto.getPluginId()).getId())
                .content(dto.getContent())
                .build();
    }
}
