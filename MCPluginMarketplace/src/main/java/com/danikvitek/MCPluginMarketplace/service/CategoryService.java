package com.danikvitek.MCPluginMarketplace.service;

import com.danikvitek.MCPluginMarketplace.api.dto.CategoryDto;
import com.danikvitek.MCPluginMarketplace.data.model.entity.Category;
import com.danikvitek.MCPluginMarketplace.data.repository.CategoryRepository;
import com.danikvitek.MCPluginMarketplace.util.exception.CategoryAlreadyExistsException;
import com.danikvitek.MCPluginMarketplace.util.exception.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import scala.util.Try;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class CategoryService {
    private final CategoryRepository categoryRepository;

    public @NotNull List<Category> fetchAll() {
        return categoryRepository.findAll();
    }

    public @NotNull Category create(String title) throws CategoryAlreadyExistsException {
        Category category = Category.builder().title(title).build();
        return Try.apply(() -> categoryRepository.save(category)).getOrElse(() -> {
            throw new CategoryAlreadyExistsException(fetchByTitle(title).getId());
        });
    }

    public void update(short id, @NotNull String title) throws CategoryNotFoundException {
        Optional<Category> category = categoryRepository.findById(id);
        category.orElseThrow(CategoryNotFoundException::new)
                .setTitle(title);
    }

    public void delete(short id) {
        categoryRepository.deleteById(id);
    }

    public @NotNull Category fetchById(short id) throws CategoryNotFoundException {
        return categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);
    }

    public @NotNull Category fetchByTitle(@NotNull String title) throws CategoryNotFoundException {
        return categoryRepository.findByTitle(title)
                .orElseThrow(CategoryNotFoundException::new);
    }
}
