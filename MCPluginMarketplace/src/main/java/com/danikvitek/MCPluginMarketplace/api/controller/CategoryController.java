package com.danikvitek.MCPluginMarketplace.api.controller;

import com.danikvitek.MCPluginMarketplace.api.dto.CategoryDto;
import com.danikvitek.MCPluginMarketplace.repo.model.entity.Category;
import com.danikvitek.MCPluginMarketplace.service.PluginService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scala.Function0;
import scala.util.Try;

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
        return Try
                .apply(() -> pluginService.fetchCategoryById(id))
                .getOrElse(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public @NotNull ResponseEntity<Void> create(@Valid @RequestBody @NotNull CategoryDto category) {
        return Try.apply(() -> {
            Try<Category> existingCategory = 
                    Try.apply(() -> pluginService.fetchCategoryByTitle(category.getTitle()));
            int id = existingCategory.isFailure()
                    ? pluginService.createCategory(category.getTitle())
                    : existingCategory.get().getId();
            String location = String.format("/categories/%d", id);
            return ResponseEntity.created(URI.create(location)).build();
        }).getOrElse(() -> ResponseEntity.badRequest().build());
    }
}
