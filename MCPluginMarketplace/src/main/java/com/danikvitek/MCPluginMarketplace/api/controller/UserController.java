package com.danikvitek.MCPluginMarketplace.api.controller;

import com.danikvitek.MCPluginMarketplace.api.dto.CommentDto;
import com.danikvitek.MCPluginMarketplace.api.dto.PluginDto;
import com.danikvitek.MCPluginMarketplace.api.dto.SimpleUserDto;
import com.danikvitek.MCPluginMarketplace.service.CommentService;
import com.danikvitek.MCPluginMarketplace.service.PluginService;
import com.danikvitek.MCPluginMarketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scala.util.Try;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public final class UserController {
    private final UserService userService;
    private final PluginService pluginService;
    private final CommentService commentService;

    @GetMapping
    public @NotNull ResponseEntity<List<SimpleUserDto>> index(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "5") int size) {
        List<SimpleUserDto> users = userService.fetchPage(page, size)
                .map(userService::userToSimpleDto).getContent();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public @NotNull ResponseEntity<SimpleUserDto> show(@PathVariable long id) {
        SimpleUserDto user = userService.userToSimpleDto(userService.fetchById(id));
        return ResponseEntity.ok(user);
    }


    @GetMapping("/{id}/authored_plugins")
    public @NotNull ResponseEntity<Set<PluginDto>> showAuthoredPlugins(@PathVariable long id) {
        Set<PluginDto> plugins = userService.fetchAuthoredPluginsById(id).stream()
                .map(pluginService::pluginToDto)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(plugins);
    }

    @GetMapping("/{id}/comments")
    public @NotNull ResponseEntity<Set<CommentDto>> showComments(@PathVariable long id) {
        Set<CommentDto> comments = commentService.fetchByUserId(id).stream()
                .map(commentService::commentToDto)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(comments);
    }
}