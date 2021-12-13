package com.danikvitek.MCPluginMarketplace.api.controller;

import com.danikvitek.MCPluginMarketplace.api.dto.SimpleUserDto;
import com.danikvitek.MCPluginMarketplace.repo.model.entity.User;
import com.danikvitek.MCPluginMarketplace.repo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public final class UserController {
    private final UserRepository userRepository;

    @GetMapping
    public @NotNull ResponseEntity<List<SimpleUserDto>> index() {
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{page}")
    public @NotNull ResponseEntity<List<SimpleUserDto>> index(@PathVariable int page) {
        if (page >= 0) {
            List<SimpleUserDto> users = userRepository.findAll(Pageable.ofSize(5).withPage(page))
                    .map(SimpleUserDto::mapFromUser).toList();
            return ResponseEntity.ok(users);
        }
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/{page}", params = {"size"})
    public @NotNull ResponseEntity<List<SimpleUserDto>> index(@PathVariable int page, @RequestParam int size) {
        if (page >= 0 && 1 <= size && size <= 20) {
            List<SimpleUserDto> users = userRepository.findAll(Pageable.ofSize(size).withPage(page))
                    .map(SimpleUserDto::mapFromUser).toList();
            return ResponseEntity.ok(users);
        }
        else return ResponseEntity.badRequest().build();
    }
}
