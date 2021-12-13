package com.danikvitek.MCPluginMarketplace.service;

import com.danikvitek.MCPluginMarketplace.repo.model.Category;
import com.danikvitek.MCPluginMarketplace.repo.model.Tag;
import com.danikvitek.MCPluginMarketplace.repo.repository.CategoryRepository;
import com.danikvitek.MCPluginMarketplace.repo.repository.PluginRepository;
import com.danikvitek.MCPluginMarketplace.repo.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import scala.NotImplementedError;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public final class PluginService {
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;
    private final PluginRepository pluginRepository;

    // Categories

    public @NotNull List<Category> fetchAllCategories() {
        return categoryRepository.findAll();
    }

    public int createCategory(String title) {
        throw new NotImplementedError();
    }

    public void updateCategory(int id, @NotNull String title) throws IllegalArgumentException {
        throw new NotImplementedError();
    }

    public void deleteCategory(int id) {
        throw new NotImplementedError();
    }

    // Tags

    public @NotNull List<Tag> fetchAllTags() {
        return tagRepository.findAll();
    }

    public @NotNull Optional<Tag> fetchTagById(long id) {
        return tagRepository.findById(id);
    }

    public @NotNull Optional<Tag> fetchTagByTitle(@NotNull String title) {
        return tagRepository.findByTitle(title);
    }
    
    public long createTag(@NotNull String title) {
        return tagRepository.save(Tag.builder().title(title).build()).getId();
    }
    
    public void deleteTag(long id) {
        tagRepository.deleteById(id);
    }
}
