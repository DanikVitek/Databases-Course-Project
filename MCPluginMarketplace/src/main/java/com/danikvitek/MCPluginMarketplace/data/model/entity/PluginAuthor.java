package com.danikvitek.MCPluginMarketplace.data.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "plugin_authors", schema = "course_project")
@IdClass(PluginAuthorPK.class)
public class PluginAuthor {
    @Id
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;
    
    @Id
    @Column(name = "plugin_id", nullable = false, updatable = false)
    private Long pluginId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PluginAuthor that = (PluginAuthor) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (pluginId != null ? !pluginId.equals(that.pluginId) : that.pluginId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (pluginId != null ? pluginId.hashCode() : 0);
        return result;
    }
}
