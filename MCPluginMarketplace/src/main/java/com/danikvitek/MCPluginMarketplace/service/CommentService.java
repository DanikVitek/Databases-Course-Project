package com.danikvitek.MCPluginMarketplace.service;

import com.danikvitek.MCPluginMarketplace.api.dto.CommentDto;
import com.danikvitek.MCPluginMarketplace.data.model.entity.Comment;
import com.danikvitek.MCPluginMarketplace.data.model.entity.CommentResponse;
import com.danikvitek.MCPluginMarketplace.data.repository.CommentRepository;
import com.danikvitek.MCPluginMarketplace.data.repository.CommentResponseRepository;
import com.danikvitek.MCPluginMarketplace.util.exception.CommentNotFoundException;
import com.danikvitek.MCPluginMarketplace.util.exception.PluginNotFoundException;
import com.danikvitek.MCPluginMarketplace.util.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public final class CommentService {
    private final CommentRepository commentRepository;
    private final CommentResponseRepository commentResponseRepository;

    private final UserService userService;
    private final PluginService pluginService;


    public Comment fetchById(long id) throws CommentNotFoundException {
        return commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
    }

    public Collection<Comment> fetchByPluginId(long pluginId) throws PluginNotFoundException {
        return commentRepository.findByPluginIdOrderById(pluginService.fetchById(pluginId).getId());
    }

    public Collection<Comment> fetchByUserId(long userId) throws UserNotFoundException {
        return commentRepository.findByUserIdOrderById(userService.fetchById(userId).getId());
    }

    public @NotNull Comment create(@NotNull CommentDto dto) throws UserNotFoundException, PluginNotFoundException {
        Comment newComment = dtoToComment(dto);
        return commentRepository.save(newComment);
    }

    public @NotNull Comment respond(long id, @NotNull CommentDto commentDto) throws IllegalArgumentException {
        Comment parentComment = fetchById(id);
        if (!Objects.equals(parentComment.getPluginId(), commentDto.getPluginId()))
            throw new IllegalArgumentException("Parent comment plugin ID must be the same for both parent and response comment");
        Comment newComment = create(commentDto);
        CommentResponse commentResponse = CommentResponse.builder()
                .parentId(parentComment.getId())
                .responseId(newComment.getId())
                .build();
        commentResponseRepository.save(commentResponse);
        return newComment;
    }

    public void delete(long id) {
        commentRepository.deleteById(id);
    }

    public @NotNull Comment update(long id, @NotNull CommentDto commentDto) throws CommentNotFoundException {
        Comment comment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
        comment.setContent(commentDto.getContent());
        return commentRepository.save(comment);
    }

    public CommentDto commentToDto(@NotNull Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .userId(comment.getUserId())
                .pluginId(comment.getPluginId())
                .content(comment.getContent())
                .publicationTime(comment.getPublicationTime())
                .responses(commentRepository.findResponsesById(comment.getId()).stream()
                        .map(this::commentToDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public Comment dtoToComment(CommentDto dto) {
        return dtoToComment(dto, false, false);
    }

    public Comment dtoToComment(@NotNull CommentDto dto, boolean includeId, boolean includeTime)
            throws UserNotFoundException, PluginNotFoundException {
        Comment.CommentBuilder builder = Comment.builder();
        if (includeId) builder = builder.id(dto.getId());
        if (includeTime) builder = builder.publicationTime(dto.getPublicationTime());
        return builder
                .userId(userService.fetchById(dto.getUserId()).getId())
                .pluginId(pluginService.fetchById(dto.getPluginId()).getId())
                .content(dto.getContent())
                .build();
    }
}
