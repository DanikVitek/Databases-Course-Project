package com.danikvitek.MCPluginMarketplace.service;

import com.danikvitek.MCPluginMarketplace.api.dto.PluginDto;
import com.danikvitek.MCPluginMarketplace.api.dto.TagDto;
import com.danikvitek.MCPluginMarketplace.data.model.entity.Category;
import com.danikvitek.MCPluginMarketplace.data.model.entity.Plugin;
import com.danikvitek.MCPluginMarketplace.data.model.entity.Tag;
import com.danikvitek.MCPluginMarketplace.data.model.entity.User;
import com.danikvitek.MCPluginMarketplace.data.repository.*;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import scala.NotImplementedError;
import scala.util.Try;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public final class PluginService {
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;
    private final PluginRepository pluginRepository;
    private final UserRepository userRepository;

    private final UserService userService;

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
    
    public @NotNull Page<Plugin> fetchAllPlugins(int page, int size) {
        return pluginRepository.findAll(Pageable.ofSize(size).withPage(page));
    }

    public @NotNull Plugin fetchPluginById(long id) throws IllegalArgumentException {
        if (id >= 1) return pluginRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plugin not fount"));
        else throw new IllegalArgumentException("ID must be >= 1");
    }
    
    public @NotNull Set<User> fetchAuthorsByPluginId(long pluginId) throws IllegalArgumentException {
        if (pluginId >= 1) return userRepository.findByAuthoredPlugin(pluginId);
        else throw new IllegalArgumentException("Plugin ID must be >= 1");
    }

    public @NotNull Plugin createPlugin(@NotNull PluginDto pluginDto) throws IllegalArgumentException {
        String categoryTitle = pluginDto.getCategoryTitle();
        Plugin plugin = pluginDtoToPlugin(pluginDto, false);
        plugin.setCategory(
                categoryRepository
                        .findByTitle(categoryTitle)
                        .orElseThrow(() -> new IllegalArgumentException(String.format("Category %s not found", categoryTitle))));
        plugin.setTags(pluginDto.getTags().stream()
                .map(t -> tagRepository.findByTitle(t).orElse(createTag(t)))
                .collect(Collectors.toSet()));
        return pluginRepository.save(plugin);
    }


    public void updatePlugin(long id, @NotNull PluginDto pluginDto) throws IllegalArgumentException {
        Plugin plugin = fetchPluginById(id);

        Set<Tag> tags = pluginDto.getTags() != null
                ? pluginDto.getTags().stream().map(this::fetchTagByTitle).collect(Collectors.toSet())
                : null;
        if (tags != null && plugin.getTags() != tags) plugin.setTags(tags);

        Set<User> authors = pluginDto.getAuthors() != null
                ? pluginDto.getAuthors().stream().map(userService::fetchByUsername).collect(Collectors.toSet())
                : null;
        if (authors != null && plugin.getAuthors() != authors) plugin.setAuthors(authors);

        String description = pluginDto.getDescription();
        if (description != null && !Objects.equals(plugin.getDescription(), description))
            plugin.setDescription(description);

        // todo: continue implementation
    }

    public PluginDto pluginToDto(@NotNull Plugin plugin) {
        return PluginDto.builder()
                .id(plugin.getId())
                .title(plugin.getTitle())
                .description(plugin.getDescription())
                .categoryTitle(plugin.getCategory().getTitle())
                .icon(plugin.getIcon())
                .price(plugin.getPrice())
                .authors(plugin.getAuthors().stream().map(User::getUsername).collect(Collectors.toSet()))
                .tags(plugin.getTags().stream().map(Tag::getTitle).collect(Collectors.toSet()))
                .build();
    }

    /**
     * Maps DTO to Plugin including ID, provided by the DTO
     *
     * @return the mapped plugin
     */
    public Plugin pluginDtoToPlugin(PluginDto pluginDto) {
        return pluginDtoToPlugin(pluginDto, true);
    }

    /**
     * Maps DTO to Plugin
     *
     * @param includeId weather to include the ID provided by the DTO
     * @return the mapped plugin
     */
    public Plugin pluginDtoToPlugin(PluginDto pluginDto, boolean includeId) {
        Plugin.PluginBuilder builder = Plugin.builder();
        if (includeId) builder.id(pluginDto.getId());
        return builder
                .title(pluginDto.getTitle())
                .description(pluginDto.getDescription())
                .category(fetchCategoryByTitle(pluginDto.getCategoryTitle()))
                .icon(pluginDto.getIcon())
                .price(pluginDto.getPrice())
                .authors(pluginDto.getAuthors().stream()
                        .map(a -> Try.apply(() -> userService.fetchByUsername(a)))
                        .filter(Try::isSuccess)
                        .map(Try::get)
                        .collect(Collectors.toSet()))
                .tags(pluginDto.getTags().stream()
                        .map(t -> Try
                                .apply(() -> fetchTagByTitle(t))
                                .getOrElse(() -> createTag(t)))
                        .collect(Collectors.toSet()))
                .build();
    }

    public TagDto tagToDto(@NotNull Tag tag) {
        return TagDto.builder()
                .id(tag.getId())
                .title(tag.getTitle())
                .build();
    }
}
