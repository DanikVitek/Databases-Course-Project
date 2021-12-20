package com.danikvitek.MCPluginMarketplace.data.repository;

import com.danikvitek.MCPluginMarketplace.data.model.entity.Comment;
import com.danikvitek.MCPluginMarketplace.data.model.entity.CommentResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CommentResponseRepository extends JpaRepository<CommentResponse, Long> {
}