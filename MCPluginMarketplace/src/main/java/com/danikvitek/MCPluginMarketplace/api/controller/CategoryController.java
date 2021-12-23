package com.danikvitek.MCPluginMarketplace.api.controller;

import com.danikvitek.MCPluginMarketplace.api.dto.CategoryDto;
import com.danikvitek.MCPluginMarketplace.config.security.UserDetailsImpl;
import com.danikvitek.MCPluginMarketplace.config.security.jwt.JwtProcessor;
import com.danikvitek.MCPluginMarketplace.data.model.entity.Category;
import com.danikvitek.MCPluginMarketplace.data.model.entity.Role;
import com.danikvitek.MCPluginMarketplace.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
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
@RequestMapping("/categories")
public final class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public @NotNull ResponseEntity<Set<CategoryDto>> index() {
        Set<CategoryDto> categories = categoryService.fetchAll().stream()
                .map(this::categoryToDto)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public @NotNull ResponseEntity<CategoryDto> show(@PathVariable short id) {
        CategoryDto category = categoryToDto(categoryService.fetchById(id));
        return ResponseEntity.ok(category);
    }

    private CategoryDto categoryToDto(@NotNull Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .title(category.getTitle())
                .build();
    }
}
