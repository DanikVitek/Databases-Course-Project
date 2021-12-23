package com.danikvitek.MCPluginMarketplace.data.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentResponsePK implements Serializable {
    @Column(name = "parent_id", nullable = false, updatable = false)
    @Id
    private Long parentId;
    
    @Column(name = "response_id", nullable = false, updatable = false)
    @Id
    private Long responseId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentResponsePK that = (CommentResponsePK) o;

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
