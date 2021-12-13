package com.danikvitek.MCPluginMarketplace.repo.model;

import com.danikvitek.MCPluginMarketplace.repo.model.embedded.CommentResponseId;
import lombok.Getter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "comment_responses")
public final class CommentResponse {
    @NotNull
    @Getter
    @EmbeddedId
    private CommentResponseId id;
}