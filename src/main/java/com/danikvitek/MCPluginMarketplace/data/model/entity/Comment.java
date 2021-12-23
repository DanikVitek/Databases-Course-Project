package com.danikvitek.MCPluginMarketplace.data.model.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments", schema = "course_project")
public class Comment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;
    
    @Column(name = "plugin_id", nullable = false, updatable = false)
    private Long pluginId;
    
    @Column(name = "content", nullable = false, length = 300)
    private String content;
    
    @Column(name = "publication_time", nullable = false, updatable = false)
    private Timestamp publicationTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (id != null ? !id.equals(comment.id) : comment.id != null) return false;
        if (userId != null ? !userId.equals(comment.userId) : comment.userId != null) return false;
        if (pluginId != null ? !pluginId.equals(comment.pluginId) : comment.pluginId != null) return false;
        if (content != null ? !content.equals(comment.content) : comment.content != null) return false;
        if (publicationTime != null ? !publicationTime.equals(comment.publicationTime) : comment.publicationTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (pluginId != null ? pluginId.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (publicationTime != null ? publicationTime.hashCode() : 0);
        return result;
    }
}
