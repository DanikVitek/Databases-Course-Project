package com.danikvitek.MCPluginMarketplace.service;

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

    public Collection<Comment> fetchResponsesById(long id) {
        return commentRepository.findResponsesById(id);
    }

    public @NotNull Comment create(long pluginId, long userId, @NotNull String content) 
            throws UserNotFoundException, PluginNotFoundException {
        Comment newComment = Comment.builder()
                .pluginId(pluginId)
                .userId(userId)
                .content(content)
                .build();
        return commentRepository.save(newComment);
    }

    public @NotNull Comment respond(long id, 
                                    long pluginId, long userId, @NotNull String content) 
            throws IllegalArgumentException {
        Comment parentComment = fetchById(id);
        if (!Objects.equals(parentComment.getPluginId(), pluginId))
            throw new IllegalArgumentException("Parent comment plugin ID must be the same for both parent and response comment");
        Comment newComment = create(pluginId, userId, content);
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

    public @NotNull Comment update(long id, @NotNull String content) throws CommentNotFoundException {
        Comment comment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
        comment.setContent(content);
        return commentRepository.save(comment);
    }
}
