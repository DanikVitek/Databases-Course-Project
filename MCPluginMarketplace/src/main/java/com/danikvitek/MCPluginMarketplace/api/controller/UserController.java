package com.danikvitek.MCPluginMarketplace.api.controller;

import com.danikvitek.MCPluginMarketplace.api.dto.PluginDto;
import com.danikvitek.MCPluginMarketplace.api.dto.SimpleUserDto;
import com.danikvitek.MCPluginMarketplace.service.PluginService;
import com.danikvitek.MCPluginMarketplace.service.UserService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping(params = { "page", "size" })
    public @NotNull ResponseEntity<List<SimpleUserDto>> index(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "5") int size) {
        if (page >= 0 && 1 <= size && size <= 20) {
            List<SimpleUserDto> users = userService.fetchPage(page, size)
                    .map(userService::userToSimpleDto).getContent();
            return ResponseEntity.ok(users);
        }
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public @NotNull ResponseEntity<SimpleUserDto> show(@PathVariable long id) {
        return Try.apply(() -> userService.fetchById(id))
                .map(userService::userToSimpleDto)
                .map(ResponseEntity::ok)
                .getOrElse(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/authored_plugins")
    public @NotNull ResponseEntity<Set<PluginDto>> showAuthoredPlugins(@PathVariable long id) {
        return Try.apply(() -> userService.fetchAuthoredPluginsById(id))
                .map(s -> s.stream()
                        .map(pluginService::pluginToDto)
                        .collect(Collectors.toSet()))
                .map(ResponseEntity::ok)
                .getOrElse(() -> ResponseEntity.notFound().build());
    }
}
