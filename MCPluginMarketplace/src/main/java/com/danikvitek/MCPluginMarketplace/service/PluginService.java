package com.danikvitek.MCPluginMarketplace.service;

import com.danikvitek.MCPluginMarketplace.api.dto.PluginDto;
import com.danikvitek.MCPluginMarketplace.api.dto.TagDto;
import com.danikvitek.MCPluginMarketplace.data.model.entity.*;
import com.danikvitek.MCPluginMarketplace.data.repository.*;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import scala.NotImplementedError;
import scala.util.Try;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public final class PluginService {
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;
    private final PluginRepository pluginRepository;
    private final UserRepository userRepository;
    private final PluginAuthorRepository pluginAuthorRepository;
    private final PluginTagRepository pluginTagRepository;

    private final UserService userService;

    // Categories

    public @NotNull List<Category> fetchAllCategories() {
        return categoryRepository.findAll();
    }

    public int createCategory(String title) {
        Category category = Category.builder().title(title).build();
        return categoryRepository.save(category).getId();
    }

    public void updateCategory(short id, @NotNull String title) throws IllegalArgumentException {
        Optional<Category> category = categoryRepository.findById(id);
        category.orElseThrow(() -> new IllegalStateException("Category not found"))
                .setTitle(title);
    }

    public void deleteCategory(short id) {
        categoryRepository.deleteById(id);
    }

    public @NotNull Category fetchCategoryById(short id) throws IllegalArgumentException {
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
        Plugin plugin = pluginDtoToPlugin(pluginDto, false);
        pluginDto.getTags().forEach(tagTitle -> {
            Tag tag;
            try {
                tag = createTag(tagTitle);
            } catch (Exception e) {
                tag = fetchTagByTitle(tagTitle);
            }
            try {
                PluginTag pluginTag = PluginTag.builder()
                        .pluginId(plugin.getId())
                        .tagId(tag.getId())
                        .build();
                pluginTagRepository.save(pluginTag);
            } catch (Exception ignored) {}
        });
        pluginDto.getAuthors().forEach(authorUsername -> {
            try {
                User author = userService.fetchByUsername(authorUsername);
                PluginAuthor pluginAuthor = PluginAuthor.builder()
                        .pluginId(plugin.getId())
                        .userId(author.getId())
                        .build();
                pluginAuthorRepository.save(pluginAuthor);
            } catch (Exception ignored) {}
        });
        return pluginRepository.save(plugin);
    }


    public void updatePlugin(long id, @NotNull PluginDto pluginDto) throws IllegalArgumentException {
        Plugin plugin = fetchPluginById(id);

        // icon, price, title
        String title = pluginDto.getTitle();
        if (title != null && !Objects.equals(plugin.getTitle(), title)) plugin.setTitle(title);

        String description = pluginDto.getDescription();
        if (description != null && !Objects.equals(plugin.getDescription(), description))
            plugin.setDescription(description);

        Category category = pluginDto.getCategoryTitle() != null
                ? fetchCategoryByTitle(pluginDto.getCategoryTitle())
                : null;
        if (category != null && !Objects.equals(plugin.getCategoryId(), category.getId()))
            plugin.setCategoryId(category.getId());

        byte[] icon = pluginDto.getIcon();
        if (icon != null && plugin.getIcon() != icon) plugin.setIcon(icon);

        BigDecimal price = pluginDto.getPrice();
        if (price != null && !Objects.equals(plugin.getPrice(), price)) plugin.setPrice(price);

        Set<User> authors = pluginDto.getAuthors() != null
                ? pluginDto.getAuthors().stream()
                .map(username -> Try.apply(() -> userService.fetchByUsername(username)))
                .filter(Try::isSuccess)
                .map(Try::get)
                .collect(Collectors.toSet())
                : null;
        if (authors != null && userRepository.findByAuthoredPlugin(plugin.getId()) != authors)
            authors.forEach(user -> {
                PluginAuthor pluginAuthor = PluginAuthor.builder()
                        .pluginId(plugin.getId())
                        .userId(user.getId())
                        .build();
                pluginAuthorRepository.save(pluginAuthor);
            });

        Set<Tag> tags = pluginDto.getTags() != null
                ? pluginDto.getTags().stream()
                .map(tagTitle -> {
                    Try<Tag> tryTag = Try.apply(() -> fetchTagByTitle(tagTitle));
                    return tryTag.getOrElse(() -> createTag(tagTitle));
                })
                .collect(Collectors.toSet())
                : null;
        if (tags != null && tagRepository.showForPlugin(plugin.getId()) != tags)
            tags.forEach(tag -> {
                PluginTag pluginTag = PluginTag.builder()
                        .pluginId(plugin.getId())
                        .tagId(tag.getId())
                        .build();
                pluginTagRepository.save(pluginTag);
            });
    }

    public PluginDto pluginToDto(@NotNull Plugin plugin) {
        return PluginDto.builder()
                .id(plugin.getId())
                .title(plugin.getTitle())
                .description(plugin.getDescription())
                .categoryTitle(fetchCategoryById(plugin.getCategoryId()).getTitle())
                .icon(plugin.getIcon())
                .price(plugin.getPrice())
                .authors(userRepository.findByAuthoredPlugin(plugin.getId()).stream()
                        .map(User::getUsername)
                        .collect(Collectors.toSet()))
                .tags(tagRepository.showForPlugin(plugin.getId()).stream()
                        .map(Tag::getTitle)
                        .collect(Collectors.toSet()))
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
                .categoryId(fetchCategoryByTitle(pluginDto.getCategoryTitle()).getId())
                .icon(pluginDto.getIcon())
                .price(pluginDto.getPrice())
                .build();
    }

    public TagDto tagToDto(@NotNull Tag tag) {
        return TagDto.builder()
                .id(tag.getId())
                .title(tag.getTitle())
                .build();
    }
}
