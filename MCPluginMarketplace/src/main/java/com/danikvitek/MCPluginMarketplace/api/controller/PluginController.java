package com.danikvitek.MCPluginMarketplace.api.controller;

import com.danikvitek.MCPluginMarketplace.api.dto.PluginDto;
import com.danikvitek.MCPluginMarketplace.api.dto.SimpleUserDto;
import com.danikvitek.MCPluginMarketplace.data.model.entity.Plugin;
import com.danikvitek.MCPluginMarketplace.service.PluginService;
import com.danikvitek.MCPluginMarketplace.service.UserService;
import com.danikvitek.MCPluginMarketplace.util.exception.AuthorsSetIsEmptyException;
import com.danikvitek.MCPluginMarketplace.util.exception.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scala.util.Try;

import javax.validation.Valid;
import java.net.URI;
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

    @GetMapping
    public @NotNull ResponseEntity<List<PluginDto>> index(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "5") int size) {
        if (page >= 0 && 1 <= size && size <= 20) {
            List<PluginDto> plugins = pluginService.fetchAll(page, size)
                    .map(pluginService::pluginToDto).getContent();
            return ResponseEntity.ok(plugins);
        } else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public @NotNull ResponseEntity<PluginDto> show(@PathVariable long id) {
        return Try.apply(() -> {
            PluginDto pluginDto = pluginService.pluginToDto(pluginService.fetchById(id));
            return ResponseEntity.ok(pluginDto);
        }).getOrElse(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/authors")
    public @NotNull ResponseEntity<Set<SimpleUserDto>> showAuthors(@PathVariable long id) {
        if (id >= 1) {
            Set<SimpleUserDto> authors = pluginService.fetchAuthorsById(id)
                    .stream()
                    .map(userService::userToSimpleDto)
                    .collect(Collectors.toSet());
            return ResponseEntity.ok(authors);
        } else return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public @NotNull ResponseEntity<Void> create(@Valid @RequestBody PluginDto pluginDto) {
//        return Try.apply(() -> {
//            Plugin plugin = pluginService.createPlugin(pluginDto);
//            String location = String.format("/plugins/%d", plugin.getId());
//            return ResponseEntity.created(URI.create(location)).build();
//        }).getOrElse(() -> ResponseEntity.notFound().build());
        try {
            Plugin plugin = pluginService.create(pluginDto);
            String location = String.format("/plugins/%d", plugin.getId());
            return ResponseEntity.created(URI.create(location)).build();
        } catch (CategoryNotFoundException e) {
            log.debug("my caught exception:\t" + e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (AuthorsSetIsEmptyException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}")
    public @NotNull ResponseEntity<Void> update(@PathVariable long id,
                                                @Valid @RequestBody PluginDto pluginDto) {
        return Try.apply(() -> {
            pluginService.update(id, pluginDto);
            String location = String.format("/plugins/%d", id);
            return ResponseEntity.created(URI.create(location)).build();
        }).getOrElse(() -> ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public @NotNull ResponseEntity<Void> delete(@PathVariable long id) {
        pluginService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
