package com.danikvitek.MCPluginMarketplace.service;

import com.danikvitek.MCPluginMarketplace.api.dto.PluginDto;
import com.danikvitek.MCPluginMarketplace.data.model.entity.*;
import com.danikvitek.MCPluginMarketplace.data.repository.*;
import com.danikvitek.MCPluginMarketplace.util.exception.AuthorsSetIsEmptyException;
import com.danikvitek.MCPluginMarketplace.util.exception.CategoryNotFoundException;
import com.danikvitek.MCPluginMarketplace.util.exception.PluginNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import scala.util.Try;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
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

    
    public @NotNull Page<Plugin> fetchAll(int page, int size) {
        return pluginRepository.findAll(Pageable.ofSize(size).withPage(page));
    }

    public @NotNull Plugin fetchById(long id) throws IllegalArgumentException, PluginNotFoundException {
        if (id >= 1) return pluginRepository.findById(id)
                .orElseThrow(PluginNotFoundException::new);
        else throw new IllegalArgumentException("ID must be >= 1");
    }
    
    public @NotNull Set<User> fetchAuthorsById(long pluginId) throws IllegalArgumentException {
        if (pluginId >= 1) return userRepository.findByAuthoredPlugin(pluginId);
        else throw new IllegalArgumentException("Plugin ID must be >= 1");
    }

    public @NotNull Plugin create(@NotNull PluginDto pluginDto) 
            throws AuthorsSetIsEmptyException, CategoryNotFoundException {
        Plugin plugin = pluginDtoToPlugin(pluginDto, false);
        Plugin savedPlugin = pluginRepository.save(plugin);
        Optional.ofNullable(pluginDto.getTags())
                .ifPresent(maybeTags -> maybeTags.forEach(tagTitle -> {
                    Tag tag;
                    try {
                        log.debug(String.format("Creating new tag \"%s\"", tagTitle));
                        tag = tagService.create(tagTitle);
                        log.info(String.format("Created new tag \"%s\"", tagTitle));
                    } catch (Exception e) {
                        log.warn("Caught exception while creating new tag:", e);
                        tag = tagService.fetchByTitle(tagTitle);
                        log.info("Fetched existing tag from DB");
                    }
                    try {
                        PluginTag pluginTag = PluginTag.builder()
                                .pluginId(savedPlugin.getId())
                                .tagId(tag.getId())
                                .build();
                        log.info(String.format("Saving plugin-tag pair for plugin \"%s\" and tag \"%s\"", savedPlugin.getTitle(), tagTitle));
                        pluginTagRepository.save(pluginTag);
                        log.info(String.format("Saved plugin-tag pair for plugin \"%s\" and tag \"%s\"", savedPlugin.getTitle(), tagTitle));
                    } catch (Exception e) {
                        log.warn("Caught the exception, while saving plugin-tag pair:", e);
                    }
                }));
        Optional.ofNullable(pluginDto.getAuthors())
                .filter(maybeAuthors -> !maybeAuthors.isEmpty())
                .orElseThrow(AuthorsSetIsEmptyException::new)
                .forEach(authorUsername -> {
                    try {
                        User author = userService.fetchByUsername(authorUsername);
                        PluginAuthor pluginAuthor = PluginAuthor.builder()
                                .pluginId(savedPlugin.getId())
                                .userId(author.getId())
                                .build();
                        log.info(String.format("Saving plugin-author pair for plugin \"%s\" and user \"%s\"", savedPlugin.getTitle(), authorUsername));
                        pluginAuthorRepository.save(pluginAuthor);
                        log.info(String.format("Saved plugin-author pair for plugin \"%s\" and user \"%s\"", savedPlugin.getTitle(), authorUsername));
                    } catch (Exception e) {
                        log.warn("Caught the exception, while saving plugin-author pair:", e);
                    }
                });
        return savedPlugin;
    }


    public void update(long id, @NotNull PluginDto pluginDto)
            throws IllegalArgumentException, PluginNotFoundException, CategoryNotFoundException {
        Plugin plugin = fetchById(id);

        String title = pluginDto.getTitle();
        if (title != null && !Objects.equals(plugin.getTitle(), title)) plugin.setTitle(title);

        String description = pluginDto.getDescription();
        if (description != null && !Objects.equals(plugin.getDescription(), description))
            plugin.setDescription(description);

        Optional.ofNullable(pluginDto.getCategoryTitle())
                .map(categoryService::fetchByTitle)
                .filter(maybeCategory -> !Objects.equals(plugin.getCategoryId(), maybeCategory.getId()))
                .ifPresent(category -> plugin.setCategoryId(category.getId()));

        byte[] icon = pluginDto.getIcon();
        if (icon != null && plugin.getIcon() != icon) plugin.setIcon(icon);

        BigDecimal price = pluginDto.getPrice();
        if (price != null && !Objects.equals(plugin.getPrice(), price)) plugin.setPrice(price);

        Optional.ofNullable(pluginDto.getAuthors())
                .map(maybeAuthors -> maybeAuthors.stream()
                        .map(username -> Try.apply(() -> userService.fetchByUsername(username)))
                        .filter(Try::isSuccess)
                        .map(Try::get)
                        .collect(Collectors.toSet()))
                .filter(maybeAuthors -> userRepository.findByAuthoredPlugin(plugin.getId()) != maybeAuthors)
                .ifPresent(authors -> authors
                        .forEach(user -> {
                            PluginAuthor pluginAuthor = PluginAuthor.builder()
                                    .pluginId(plugin.getId())
                                    .userId(user.getId())
                                    .build();
                            pluginAuthorRepository.save(pluginAuthor);
                        }));

        Optional.ofNullable(pluginDto.getTags())
                .map(maybeTags -> maybeTags.stream()
                        .map(tagTitle -> Try
                                .apply(() -> tagService.fetchByTitle(tagTitle))
                                .getOrElse(() -> tagService.create(tagTitle)))
                        .collect(Collectors.toSet()))
                .filter(maybeTags -> tagService.fetchByPlugin(plugin.getId()) != maybeTags)
                .ifPresent(tags -> tags
                        .forEach(tag -> {
                            PluginTag pluginTag = PluginTag.builder()
                                    .pluginId(plugin.getId())
                                    .tagId(tag.getId())
                                    .build();
                            pluginTagRepository.save(pluginTag);
                        }));
    }

    public void delete(long id) {
        pluginRepository.findById(id).ifPresent(pluginRepository::delete);
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
    public Plugin pluginDtoToPlugin(PluginDto pluginDto, boolean includeId) throws CategoryNotFoundException {
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
