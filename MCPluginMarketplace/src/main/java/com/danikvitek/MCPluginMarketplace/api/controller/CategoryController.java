package com.danikvitek.MCPluginMarketplace.api.controller;

import com.danikvitek.MCPluginMarketplace.api.dto.CategoryDto;
import com.danikvitek.MCPluginMarketplace.repo.model.entity.Category;
import com.danikvitek.MCPluginMarketplace.service.PluginService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public final class CategoryController {

    private final PluginService pluginService;

    @GetMapping
    public @NotNull ResponseEntity<List<Category>> index() {
        return ResponseEntity.ok(pluginService.fetchAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> show(@PathVariable int id) {
        try {
            Category category = pluginService.fetchCategoryById(id);
            return ResponseEntity.ok(category);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public @NotNull ResponseEntity<Void> create(@Valid @RequestBody @NotNull CategoryDto category) {
        try {
            Optional<Category> existingCategory;
            try {
                existingCategory = Optional.of(pluginService.fetchCategoryByTitle(category.getTitle()));
            } catch (IllegalArgumentException e) {
                existingCategory = Optional.empty();
            }
            int id = existingCategory.isEmpty()
                    ? pluginService.createCategory(category.getTitle())
                    : existingCategory.get().getId();
            String location = String.format("/categories/%d", id);
            return ResponseEntity.created(URI.create(location)).build();
        } catch (ConstraintViolationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
