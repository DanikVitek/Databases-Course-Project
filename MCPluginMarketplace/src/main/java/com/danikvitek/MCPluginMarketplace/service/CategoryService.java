package com.danikvitek.MCPluginMarketplace.service;

import com.danikvitek.MCPluginMarketplace.data.model.entity.Category;
import com.danikvitek.MCPluginMarketplace.data.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class CategoryService {
    private final CategoryRepository categoryRepository;

    public @NotNull List<Category> fetchAll() {
        return categoryRepository.findAll();
    }

    public int create(String title) {
        Category category = Category.builder().title(title).build();
        return categoryRepository.save(category).getId();
    }

    public void update(short id, @NotNull String title) throws IllegalArgumentException {
        Optional<Category> category = categoryRepository.findById(id);
        category.orElseThrow(() -> new IllegalStateException("Category not found"))
                .setTitle(title);
    }

    public void delete(short id) {
        categoryRepository.deleteById(id);
    }

    public @NotNull Category fetchById(short id) throws IllegalArgumentException {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Category not found"));
    }

    public @NotNull Category fetchByTitle(@NotNull String title) throws IllegalArgumentException {
        return categoryRepository.findByTitle(title)
                .orElseThrow(() -> new IllegalStateException("Tag not found"));
    }
}
