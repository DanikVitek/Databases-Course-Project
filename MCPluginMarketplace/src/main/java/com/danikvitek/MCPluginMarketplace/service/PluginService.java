package com.danikvitek.MCPluginMarketplace.service;

import com.danikvitek.MCPluginMarketplace.api.dto.SimpleUserDto;
import com.danikvitek.MCPluginMarketplace.repo.model.Category;
import com.danikvitek.MCPluginMarketplace.repo.model.Plugin;
import com.danikvitek.MCPluginMarketplace.repo.model.Tag;
import com.danikvitek.MCPluginMarketplace.repo.model.User;
import com.danikvitek.MCPluginMarketplace.repo.repository.*;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import scala.NotImplementedError;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public final class PluginService {
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;
    private final PluginRepository pluginRepository;
    private final PluginAuthorRepository pluginAuthorRepository;
    private final UserRepository userRepository;

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

    public long createTag(@NotNull String title) {
        Tag tag = Tag.builder().title(title).build();
        return tagRepository.save(tag).getId();
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
    
    public @NotNull List<SimpleUserDto> fetchAuthorsById(long pluginId) throws IllegalArgumentException {
        return pluginAuthorRepository.findAllById_PluginId(pluginId).stream()
//                .map(pa -> userRepository.findById(pa.getId().getUserId()).get())
                .map(u -> SimpleUserDto.builder()
                        .username(u.getUsername())
                        .firstName(u.getFirstName())
                        .lastName(u.getLastName())
                        .email(u.getEmail())
                        .role(u.getRole())
                        .build()
        ).collect(Collectors.toList());
    }
}
