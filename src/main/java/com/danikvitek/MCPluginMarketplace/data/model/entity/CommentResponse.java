package com.danikvitek.MCPluginMarketplace.data.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment_responses", schema = "course_project")
@IdClass(CommentResponsePK.class)
public class CommentResponse {
    @Id
    @Column(name = "parent_id", nullable = false, updatable = false)
    private Long parentId;
    
    @Id
    @Column(name = "response_id", nullable = false, updatable = false)
    private Long responseId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentResponse that = (CommentResponse) o;

        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) return false;
        if (responseId != null ? !responseId.equals(that.responseId) : that.responseId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = parentId != null ? parentId.hashCode() : 0;
        result = 31 * result + (responseId != null ? responseId.hashCode() : 0);
        return result;
    }
}
