package com.danikvitek.MCPluginMarketplace.api.controller;

import com.danikvitek.MCPluginMarketplace.api.dto.PluginDto;
import com.danikvitek.MCPluginMarketplace.api.dto.SimpleUserDto;
import com.danikvitek.MCPluginMarketplace.repo.model.entity.Plugin;
import com.danikvitek.MCPluginMarketplace.repo.model.entity.User;
import com.danikvitek.MCPluginMarketplace.service.PluginService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/plugins")
public final class PluginController {
    private final PluginService pluginService;

    @GetMapping("/{page}")
    public @NotNull ResponseEntity<List<PluginDto>> index(@PathVariable int page) {
        if (page >= 0) {
            List<PluginDto> plugins = pluginService.fetchAllPlugins(page, 5).stream()
                    .map(PluginDto::mapFromPlugin).collect(Collectors.toList());
            return ResponseEntity.ok(plugins);
        }
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/{page}", params = {"size"})
    public @NotNull ResponseEntity<List<PluginDto>> indexWithSize(@PathVariable int page, @RequestParam int size) {
        if (page >= 0 && 1 <= size && size <= 20) {
            List<PluginDto> plugins = pluginService.fetchAllPlugins(page, size).stream()
                    .map(PluginDto::mapFromPlugin).collect(Collectors.toList());
            return ResponseEntity.ok(plugins);
        }
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/id/{id}")
    public @NotNull ResponseEntity<PluginDto> show(@PathVariable long id) {
        if (id >= 1) {
            try {
                PluginDto plugin = PluginDto.mapFromPlugin(pluginService.fetchPluginById(id));
                return ResponseEntity.ok(plugin);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.notFound().build();
            }
        }
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/id/{id}/authors")
    public @NotNull ResponseEntity<Set<SimpleUserDto>> showAuthors(@PathVariable long id) {
        if (id >= 1) {
            Set<SimpleUserDto> authors = pluginService.fetchAuthorsByPluginId(id)
                    .stream()
                    .map(SimpleUserDto::mapFromUser)
                    .collect(Collectors.toSet());
            return ResponseEntity.ok(authors);
        }
        else return ResponseEntity.badRequest().build();
    }

//    @PostMapping("/plugin")
//    public @NotNull ResponseEntity<Void> create(@Valid @RequestBody PluginDto plugin) {
//        try {
//
//        }
//    }
}
