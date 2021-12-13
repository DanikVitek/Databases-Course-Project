package com.danikvitek.MCPluginMarketplace.repo.model.embedded;

import lombok.Getter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Embeddable
public final class CommentResponseId implements Serializable {
    private static final long serialVersionUID = -4263577234935399994L;
    
    @Positive
    @NotNull
    @Column(name = "parent_id", nullable = false, updatable = false)
    private Long parentId;
    
    @Positive
    @NotNull
    @Column(name = "response_id", nullable = false, updatable = false)
    private Long responseId;

    @Override
    public int hashCode() {
        return Objects.hash(parentId, responseId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CommentResponseId entity = (CommentResponseId) o;
        return Objects.equals(this.parentId, entity.parentId) &&
                Objects.equals(this.responseId, entity.responseId);
    }
}