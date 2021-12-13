package com.danikvitek.MCPluginMarketplace.api.controller;

import com.danikvitek.MCPluginMarketplace.api.dto.TagDto;
import com.danikvitek.MCPluginMarketplace.repo.model.Tag;
import com.danikvitek.MCPluginMarketplace.service.PluginService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tags")
public final class TagController {

    private final PluginService pluginService;

    @GetMapping
    public @NotNull ResponseEntity<List<Tag>> index() {
        return ResponseEntity.ok(pluginService.fetchAllTags());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> show(@PathVariable long id) {
        try {
            Tag tag = pluginService.fetchTagById(id);
            return ResponseEntity.ok(tag);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public @NotNull ResponseEntity<Void> create(@Valid @RequestBody @NotNull TagDto tag) {
        try {
            Optional<Tag> existingTag;
            try {
                existingTag = Optional.of(pluginService.fetchTagByTitle(tag.getTitle()));
            } catch (IllegalArgumentException e) {
                existingTag = Optional.empty();
            }
            long id = existingTag.isEmpty()
                    ? pluginService.createTag(tag.getTitle())
                    : existingTag.get().getId();
            String location = String.format("/tags/%d", id);
            return ResponseEntity.created(URI.create(location)).build();
        } catch (ConstraintViolationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public @NotNull ResponseEntity<Void> delete(@PathVariable long id) {
        pluginService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
