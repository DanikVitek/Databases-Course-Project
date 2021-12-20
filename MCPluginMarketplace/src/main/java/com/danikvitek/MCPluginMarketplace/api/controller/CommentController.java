package com.danikvitek.MCPluginMarketplace.api.controller;

import com.danikvitek.MCPluginMarketplace.api.dto.CommentDto;
import com.danikvitek.MCPluginMarketplace.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public final class CommentController {
    private final CommentService commentService;

    @GetMapping("/{id}")
    public @NotNull ResponseEntity<CommentDto> show(@PathVariable long id) {
        CommentDto commentDto = commentService.commentToDto(commentService.fetchById(id));
        return ResponseEntity.ok(commentDto);
    }

    @PostMapping
    public @NotNull ResponseEntity<Void> create(@Valid @RequestBody CommentDto commentDto) {
        long commentId = commentService.create(commentDto).getId();
        String location = String.format("/comments/%d", commentId);
        return ResponseEntity.created(URI.create(location)).build();
    }
    
    @PostMapping("/{id}")
    public @NotNull ResponseEntity<Void> respond(@PathVariable long id,
                                                 @Valid @RequestBody CommentDto commentDto) {
        long commentId = commentService.respond(id, commentDto).getId();
        String location = String.format("/comments/%d", commentId);
        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/{id}")
    public @NotNull ResponseEntity<CommentDto> update(@PathVariable long id,
                                                      @Valid @RequestBody CommentDto commentDto) {
        CommentDto updatedComment = commentService.commentToDto(commentService.update(id, commentDto));
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{id}")
    public @NotNull ResponseEntity<Void> delete(@PathVariable long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
