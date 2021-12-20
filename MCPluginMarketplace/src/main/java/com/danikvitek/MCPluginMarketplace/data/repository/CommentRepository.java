package com.danikvitek.MCPluginMarketplace.data.repository;

import com.danikvitek.MCPluginMarketplace.data.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUserIdOrderById(long userId);

    List<Comment> findByPluginIdOrderById(long pluginId);

    List<Comment> findByUserIdAndPluginIdOrderByPublicationTimeDesc(long userId, long pluginId);

    @Query("select r from Comment c " +
            "join CommentResponse cr on c.id = cr.parentId " +
            "join Comment r on cr.responseId = r.id where c.id = ?1 " +
            "order by r.id")
    List<Comment> findResponsesById(long id);
}
