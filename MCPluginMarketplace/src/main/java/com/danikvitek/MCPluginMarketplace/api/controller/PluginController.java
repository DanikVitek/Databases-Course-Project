package com.danikvitek.MCPluginMarketplace.api.controller;

import com.danikvitek.MCPluginMarketplace.api.dto.CommentDto;
import com.danikvitek.MCPluginMarketplace.api.dto.PluginDto;
import com.danikvitek.MCPluginMarketplace.api.dto.SimpleUserDto;
import com.danikvitek.MCPluginMarketplace.data.model.entity.Plugin;
import com.danikvitek.MCPluginMarketplace.service.CommentService;
import com.danikvitek.MCPluginMarketplace.service.PluginService;
import com.danikvitek.MCPluginMarketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public @NotNull ResponseEntity<List<PluginDto>> index(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "5") int size) {
        List<PluginDto> plugins = pluginService.fetchAll(page, size)
                .map(pluginService::pluginToDto).getContent();
        return ResponseEntity.ok(plugins);
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
                .map(commentService::commentToDto)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(comments);
    }
    
    @PostMapping
    public @NotNull ResponseEntity<Void> create(@Valid @RequestBody PluginDto pluginDto) {
        Plugin plugin = pluginService.create(pluginDto);
        String location = String.format("/plugins/%d", plugin.getId());
        return ResponseEntity.created(URI.create(location)).build();
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
}
