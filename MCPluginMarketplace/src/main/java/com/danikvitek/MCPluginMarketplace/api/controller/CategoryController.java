package com.danikvitek.MCPluginMarketplace.api.controller;

import com.danikvitek.MCPluginMarketplace.api.dto.CategoryDto;
import com.danikvitek.MCPluginMarketplace.data.model.entity.Category;
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

    private final PluginService pluginService;

    @GetMapping
    public @NotNull ResponseEntity<List<Category>> index() {
        return ResponseEntity.ok(pluginService.fetchAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> show(@PathVariable int id) {
        return Try
                .apply(() -> pluginService.fetchCategoryById(id))
                .getOrElse(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public @NotNull ResponseEntity<Void> create(@Valid @RequestBody @NotNull CategoryDto categoryDto) {
        return Try.apply(() -> {
            Try<Category> existingCategory = 
                    Try.apply(() -> pluginService.fetchCategoryByTitle(categoryDto.getTitle()));
            int id = existingCategory.isFailure()
                    ? pluginService.createCategory(categoryDto.getTitle())
                    : existingCategory.get().getId();
            String location = String.format("/categories/%d", id);
            return ResponseEntity.created(URI.create(location)).build();
        }).getOrElse(() -> ResponseEntity.badRequest().build());
    }
    
    @PatchMapping("/{id}")
    public @NotNull ResponseEntity<Void> update(@PathVariable int id, 
                                                @Valid @RequestBody @NotNull CategoryDto categoryDto) {
        try {
            pluginService.updateCategory(id, categoryDto.getTitle());
            String location = String.format("/categories/%d", id);
            return ResponseEntity.created(URI.create(location)).build();
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public @NotNull ResponseEntity<Void> delete(@PathVariable int id) {
        pluginService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
