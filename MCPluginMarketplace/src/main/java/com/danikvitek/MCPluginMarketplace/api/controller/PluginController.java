package com.danikvitek.MCPluginMarketplace.api.controller;

import com.danikvitek.MCPluginMarketplace.api.dto.PluginDto;
import com.danikvitek.MCPluginMarketplace.api.dto.SimpleUserDto;
import com.danikvitek.MCPluginMarketplace.data.model.entity.Plugin;
import com.danikvitek.MCPluginMarketplace.service.PluginService;
import com.danikvitek.MCPluginMarketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scala.util.Try;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/plugins")
public final class PluginController {
    private final PluginService pluginService;
    private final UserService userService;

    @GetMapping(params = { "page", "size" }) 
    public @NotNull ResponseEntity<List<PluginDto>> index(@RequestParam(defaultValue = "0") int page, 
                                                          @RequestParam(defaultValue = "5") int size) {
        if (page >= 0 && 1 <= size && size <= 20) {
            List<PluginDto> plugins = pluginService.fetchAllPlugins(page, size)
                    .map(pluginService::pluginToDto).getContent();
            return ResponseEntity.ok(plugins);
        }
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public @NotNull ResponseEntity<PluginDto> show(@PathVariable long id) {
        return Try.apply(() -> {
            PluginDto pluginDto = pluginService.pluginToDto(pluginService.fetchPluginById(id));
            return ResponseEntity.ok(pluginDto);
        }).getOrElse(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/authors")
    public @NotNull ResponseEntity<Set<SimpleUserDto>> showAuthors(@PathVariable long id) {
        if (id >= 1) {
            Set<SimpleUserDto> authors = pluginService.fetchAuthorsByPluginId(id)
                    .stream()
                    .map(userService::userToSimpleDto)
                    .collect(Collectors.toSet());
            return ResponseEntity.ok(authors);
        }
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public @NotNull ResponseEntity<Void> create(@Valid @RequestBody PluginDto pluginDto) {
        return Try.apply(() -> {
            Plugin plugin = pluginService.createPlugin(pluginDto);
            String location = String.format("/plugins/%d", plugin.getId());
            return ResponseEntity.created(URI.create(location)).build();
        }).getOrElse(() -> ResponseEntity.notFound().build());
    }
    
    @PatchMapping("/{id}")
    public @NotNull ResponseEntity<Void> update(@PathVariable long id, @Valid @RequestBody PluginDto pluginDto) {
        return Try.apply(() -> {
            pluginService.updatePlugin(id, pluginDto);
            String location = String.format("/plugins/%d", id);
            return ResponseEntity.created(URI.create(location)).build();
        }).getOrElse(() -> ResponseEntity.notFound().build());
    }
}
