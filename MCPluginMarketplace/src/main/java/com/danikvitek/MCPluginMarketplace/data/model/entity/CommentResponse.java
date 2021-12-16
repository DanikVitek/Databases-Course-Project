package com.danikvitek.MCPluginMarketplace.data.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment_responses", schema = "course_project")
@IdClass(CommentResponsePK.class)
public class CommentResponse {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "parent_id", nullable = false)
    private Long parentId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "response_id", nullable = false)
    private Long responseId;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getResponseId() {
        return responseId;
    }

    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }

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
