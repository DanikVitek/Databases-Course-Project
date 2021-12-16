package com.danikvitek.MCPluginMarketplace.api.controller;

import com.danikvitek.MCPluginMarketplace.api.dto.CategoryDto;
import com.danikvitek.MCPluginMarketplace.data.model.entity.Category;
import com.danikvitek.MCPluginMarketplace.service.CategoryService;
import com.danikvitek.MCPluginMarketplace.service.PluginService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scala.util.Try;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public final class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public @NotNull ResponseEntity<List<Category>> index() {
        return ResponseEntity.ok(categoryService.fetchAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> show(@PathVariable short id) {
        return Try
                .apply(() -> categoryService.fetchById(id))
                .getOrElse(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public @NotNull ResponseEntity<Void> create(@Valid @RequestBody @NotNull CategoryDto categoryDto) {
        return Try.apply(() -> {
            Try<Category> existingCategory = 
                    Try.apply(() -> categoryService.fetchByTitle(categoryDto.getTitle()));
            int id = existingCategory.isFailure()
                    ? categoryService.create(categoryDto.getTitle())
                    : existingCategory.get().getId();
            String location = String.format("/categories/%d", id);
            return ResponseEntity.created(URI.create(location)).build();
        }).getOrElse(() -> ResponseEntity.badRequest().build());
    }
    
    @PatchMapping("/{id}")
    public @NotNull ResponseEntity<Void> update(@PathVariable short id, 
                                                @Valid @RequestBody @NotNull CategoryDto categoryDto) {
        try {
            categoryService.update(id, categoryDto.getTitle());
            String location = String.format("/categories/%d", id);
            return ResponseEntity.created(URI.create(location)).build();
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public @NotNull ResponseEntity<Void> delete(@PathVariable short id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
