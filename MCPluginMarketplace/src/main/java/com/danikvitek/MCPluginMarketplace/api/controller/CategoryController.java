package com.danikvitek.MCPluginMarketplace.api.controller;

import com.danikvitek.MCPluginMarketplace.api.dto.CategoryDto;
import com.danikvitek.MCPluginMarketplace.data.model.entity.Category;
import com.danikvitek.MCPluginMarketplace.service.CategoryService;
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
@RequestMapping("/categories")
public final class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public @NotNull ResponseEntity<Set<CategoryDto>> index() {
        Set<CategoryDto> categories = categoryService.fetchAll().stream()
                .map(categoryService::categoryToDto)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public @NotNull ResponseEntity<CategoryDto> show(@PathVariable short id) {
        CategoryDto category = categoryService.categoryToDto(categoryService.fetchById(id));
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public @NotNull ResponseEntity<Void> create(@Valid @RequestBody @NotNull CategoryDto categoryDto) {
        int id = categoryService.create(categoryDto.getTitle()).getId();
        String location = String.format("/categories/%d", id);
        return ResponseEntity.created(URI.create(location)).build();
    }
    
    @PatchMapping("/{id}")
    public @NotNull ResponseEntity<Void> update(@PathVariable short id, 
                                                @Valid @RequestBody @NotNull CategoryDto categoryDto) {
        categoryService.update(id, categoryDto.getTitle());
        String location = String.format("/categories/%d", id);
        return ResponseEntity.created(URI.create(location)).build();
    }
    
    @DeleteMapping("/{id}")
    public @NotNull ResponseEntity<Void> delete(@PathVariable short id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
