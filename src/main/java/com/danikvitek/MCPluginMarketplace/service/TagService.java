package com.danikvitek.MCPluginMarketplace.service;

import com.danikvitek.MCPluginMarketplace.api.dto.TagDto;
import com.danikvitek.MCPluginMarketplace.data.model.entity.Tag;
import com.danikvitek.MCPluginMarketplace.data.repository.TagRepository;
import com.danikvitek.MCPluginMarketplace.util.exception.TagAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import scala.util.Try;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public final class TagService {
    private final TagRepository tagRepository;

    public @NotNull List<Tag> fetchAll(int page, int size) {
        return tagRepository.findAll(Pageable.ofSize(size).withPage(page)).getContent();
    }

    public @NotNull Tag fetchById(long id) throws IllegalArgumentException {
        return tagRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Tag not found"));
    }

    public @NotNull Tag fetchByTitle(@NotNull String title) throws IllegalArgumentException {
        return tagRepository.findByTitle(title)
                .orElseThrow(() -> new IllegalStateException("Tag not found"));
    }

    public Set<Tag> fetchByPlugin(long pluginId) throws IllegalArgumentException {
        return tagRepository.showForPlugin(pluginId);
    }
    
    public @NotNull Tag create(@NotNull String title) throws TagAlreadyExistsException {
        Tag tag = Tag.builder().title(title).build();
        return Try.apply(() -> tagRepository.save(tag)).getOrElse(() -> {
            throw new TagAlreadyExistsException(fetchByTitle(title).getId());
        });
    }

    public void delete(long id) {
        tagRepository.deleteById(id);
    }

    public TagDto tagToDto(@NotNull Tag tag) {
        return TagDto.builder()
                .id(tag.getId())
                .title(tag.getTitle())
                .build();
    }
}
