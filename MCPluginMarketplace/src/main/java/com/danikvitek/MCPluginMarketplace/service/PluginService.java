package com.danikvitek.MCPluginMarketplace.service;

import com.danikvitek.MCPluginMarketplace.api.dto.PluginDto;
import com.danikvitek.MCPluginMarketplace.repo.model.entity.Category;
import com.danikvitek.MCPluginMarketplace.repo.model.entity.Plugin;
import com.danikvitek.MCPluginMarketplace.repo.model.entity.Tag;
import com.danikvitek.MCPluginMarketplace.repo.model.entity.User;
import com.danikvitek.MCPluginMarketplace.repo.repository.*;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import scala.NotImplementedError;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        Optional<Category> category = categoryRepository.findById(id);
        category.orElseThrow(() -> new IllegalStateException("Category not found"))
                .setTitle(title);
    }

    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }

    public @NotNull Category fetchCategoryById(int id) throws IllegalArgumentException {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Category not found"));
    }

    public @NotNull Category fetchCategoryByTitle(@NotNull String title) throws IllegalArgumentException {
        return categoryRepository.findByTitle(title)
                .orElseThrow(() -> new IllegalStateException("Tag not found"));
    }

    // Tags

    public @NotNull List<Tag> fetchAllTags() {
        return tagRepository.findAll();
    }

    public @NotNull Tag fetchTagById(long id) throws IllegalArgumentException {
        return tagRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Tag not found"));
    }

    public @NotNull Tag fetchTagByTitle(@NotNull String title) throws IllegalArgumentException {
        return tagRepository.findByTitle(title)
                .orElseThrow(() -> new IllegalStateException("Tag not found"));
    }

    public @NotNull Tag createTag(@NotNull String title) {
        Tag tag = Tag.builder().title(title).build();
        return tagRepository.save(tag);
    }

    public void deleteTag(long id) {
        tagRepository.deleteById(id);
    }

    
    // Plugins
    
    public @NotNull List<Plugin> fetchAllPlugins(int page, int size) {
        return pluginRepository.findAll(Pageable.ofSize(size).withPage(page)).getContent();
    }

    public @NotNull Plugin fetchPluginById(long id) throws IllegalArgumentException {
        return pluginRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plugin not fount"));
    }
    
    public @NotNull Set<User> fetchAuthorsByPluginId(long pluginId) throws IllegalArgumentException {
        Plugin plugin = pluginRepository.findById(pluginId)
                .orElseThrow(() -> new IllegalStateException("Plugin not found"));
        return plugin.getAuthors();
//        return userRepository.findByAuthoredPlugin(
//                pluginRepository.findById(pluginId)
//                        .orElseThrow(() -> new IllegalStateException("Plugin not found")));
    }

    public @NotNull Plugin createPlugin(@NotNull PluginDto pluginDto) throws IllegalArgumentException {
        String categoryTitle = pluginDto.getCategoryTitle();
        Plugin plugin = pluginDto.mapToPlugin();
        plugin.setCategory(
                categoryRepository
                        .findByTitle(categoryTitle)
                        .orElseThrow(() -> new IllegalArgumentException(String.format("Category %s not found", categoryTitle))));
        plugin.setTags(pluginDto.getTags().stream()
                .map(t -> tagRepository.findByTitle(t).orElse(createTag(t)))
                .collect(Collectors.toSet()));
        return pluginRepository.save(plugin);
    }
}
