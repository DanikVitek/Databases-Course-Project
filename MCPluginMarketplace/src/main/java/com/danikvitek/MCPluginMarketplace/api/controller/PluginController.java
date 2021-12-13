package com.danikvitek.MCPluginMarketplace.api.controller;

import com.danikvitek.MCPluginMarketplace.api.dto.SimpleUserDto;
import com.danikvitek.MCPluginMarketplace.repo.model.Plugin;
import com.danikvitek.MCPluginMarketplace.service.PluginService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/plugins")
public final class PluginController {
    private final PluginService pluginService;

    @GetMapping("/{page}")
    public @NotNull ResponseEntity<List<Plugin>> index(@PathVariable int page) {
        List<Plugin> plugins = pluginService.fetchAllPlugins(page, 5);
        return ResponseEntity.ok(plugins);
    }

    @GetMapping(value = "/{page}", params = {"size"})
    public @NotNull ResponseEntity<List<Plugin>> indexWithSize(@PathVariable int page, @RequestParam int size) {
        if (1 <= size && size <= 20) {
            List<Plugin> plugins = pluginService.fetchAllPlugins(page, size);
            return ResponseEntity.ok(plugins);
        }
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/id/{id}")
    public @NotNull ResponseEntity<Plugin> show(@PathVariable long id) {
        try {
            Plugin plugin = pluginService.fetchPluginById(id);
            return ResponseEntity.ok(plugin);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/id/{id}/authors")
    public @NotNull ResponseEntity<List<SimpleUserDto>> showAuthors(@PathVariable long id) {
        List<SimpleUserDto> authors = pluginService.fetchAuthorsById(id);
        return ResponseEntity.ok(authors);
    }
}
