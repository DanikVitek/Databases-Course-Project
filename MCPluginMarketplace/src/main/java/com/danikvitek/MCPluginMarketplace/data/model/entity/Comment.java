package com.danikvitek.MCPluginMarketplace.data.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments", schema = "course_project")
public class Comment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Basic
    @Column(name = "plugin_id", nullable = false)
    private Long pluginId;
    @Basic
    @Column(name = "content", nullable = false, length = 300)
    private String content;
    @Basic
    @Column(name = "publication_time", nullable = false)
    private Timestamp publicationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPluginId() {
        return pluginId;
    }

    public void setPluginId(Long pluginId) {
        this.pluginId = pluginId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getPublicationTime() {
        return publicationTime;
    }

    public void setPublicationTime(Timestamp publicationTime) {
        this.publicationTime = publicationTime;
    }

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
