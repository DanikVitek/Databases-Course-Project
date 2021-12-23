package com.danikvitek.MCPluginMarketplace.api.controller;

import com.danikvitek.MCPluginMarketplace.api.dto.TagDto;
import com.danikvitek.MCPluginMarketplace.data.model.entity.Tag;
import com.danikvitek.MCPluginMarketplace.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scala.util.Try;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/tags")
public final class TagController {
    private final TagService tagService;

    @GetMapping
    public @NotNull ResponseEntity<Collection<TagDto>> index(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "5") int size) {
        if (size <= 30) {
            Collection<TagDto> tags = tagService.fetchAll(page, size).stream()
                    .map(tagService::tagToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(tags);
        } else throw new IllegalArgumentException("Page size must not be greater than thirty!");
    }

    @GetMapping("/{id}")
    public @NotNull ResponseEntity<TagDto> show(@PathVariable long id) {
        TagDto tag = tagService.tagToDto(tagService.fetchById(id));
        return ResponseEntity.ok(tag);
    }

    @PostMapping
    public @NotNull ResponseEntity<Void> create(@Valid @RequestBody @NotNull TagDto tag) {
        long id = tagService.create(tag.getTitle()).getId();
        String location = String.format("/tags/%d", id);
        return ResponseEntity.created(URI.create(location)).build();
    }

    @DeleteMapping("/{id}")
    public @NotNull ResponseEntity<Void> delete(@PathVariable long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
