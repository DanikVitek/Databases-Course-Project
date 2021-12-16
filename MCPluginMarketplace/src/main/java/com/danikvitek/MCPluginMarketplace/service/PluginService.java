package com.danikvitek.MCPluginMarketplace.service;

import com.danikvitek.MCPluginMarketplace.api.dto.PluginDto;
import com.danikvitek.MCPluginMarketplace.data.model.entity.*;
import com.danikvitek.MCPluginMarketplace.data.repository.*;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import scala.util.Try;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public final class PluginService {
    private final PluginRepository pluginRepository;
    private final UserRepository userRepository;
    private final PluginAuthorRepository pluginAuthorRepository;
    private final PluginTagRepository pluginTagRepository;

    private final UserService userService;
    private final CategoryService categoryService;
    private final TagService tagService;

    
    // Plugins
    
    public @NotNull Page<Plugin> fetchAll(int page, int size) {
        return pluginRepository.findAll(Pageable.ofSize(size).withPage(page));
    }

    public @NotNull Plugin fetchById(long id) throws IllegalArgumentException {
        if (id >= 1) return pluginRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plugin not fount"));
        else throw new IllegalArgumentException("ID must be >= 1");
    }
    
    public @NotNull Set<User> fetchAuthorsById(long pluginId) throws IllegalArgumentException {
        if (pluginId >= 1) return userRepository.findByAuthoredPlugin(pluginId);
        else throw new IllegalArgumentException("Plugin ID must be >= 1");
    }

    public @NotNull Plugin create(@NotNull PluginDto pluginDto) throws IllegalArgumentException {
        Plugin plugin = pluginDtoToPlugin(pluginDto, false);
        Plugin savedPlugin = pluginRepository.save(plugin);
        pluginDto.getTags().forEach(tagTitle -> {
            Tag tag;
            try {
                tag = tagService.create(tagTitle);
            } catch (Exception e) {
                tag = tagService.fetchByTitle(tagTitle);
            }
            try {
                PluginTag pluginTag = PluginTag.builder()
                        .pluginId(savedPlugin.getId())
                        .tagId(tag.getId())
                        .build();
                pluginTagRepository.save(pluginTag);
            } catch (Exception ignored) {}
        });
        pluginDto.getAuthors().forEach(authorUsername -> {
            try {
                User author = userService.fetchByUsername(authorUsername);
                PluginAuthor pluginAuthor = PluginAuthor.builder()
                        .pluginId(savedPlugin.getId())
                        .userId(author.getId())
                        .build();
                pluginAuthorRepository.save(pluginAuthor);
            } catch (Exception ignored) {}
        });
        return savedPlugin;
    }


    public void update(long id, @NotNull PluginDto pluginDto) throws IllegalArgumentException {
        Plugin plugin = fetchById(id);
        
        String title = pluginDto.getTitle();
        if (title != null && !Objects.equals(plugin.getTitle(), title)) plugin.setTitle(title);

        String description = pluginDto.getDescription();
        if (description != null && !Objects.equals(plugin.getDescription(), description))
            plugin.setDescription(description);

        Category category = pluginDto.getCategoryTitle() != null
                ? categoryService.fetchByTitle(pluginDto.getCategoryTitle())
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
                    Try<Tag> tryTag = Try.apply(() -> tagService.fetchByTitle(tagTitle));
                    return tryTag.getOrElse(() -> tagService.create(tagTitle));
                })
                .collect(Collectors.toSet())
                : null;
        if (tags != null && tagService.fetchByPlugin(plugin.getId()) != tags)
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
                .categoryTitle(categoryService.fetchById(plugin.getCategoryId()).getTitle())
                .icon(plugin.getIcon())
                .price(plugin.getPrice())
                .authors(userRepository.findByAuthoredPlugin(plugin.getId()).stream()
                        .map(User::getUsername)
                        .collect(Collectors.toSet()))
                .tags(tagService.fetchByPlugin(plugin.getId()).stream()
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
                .categoryId(categoryService.fetchByTitle(pluginDto.getCategoryTitle()).getId())
                .icon(pluginDto.getIcon())
                .price(pluginDto.getPrice())
                .build();
    }
}
