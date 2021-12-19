package com.danikvitek.MCPluginMarketplace.data.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "supported_game_versions", schema = "course_project")
@IdClass(SupportedGameVersionPK.class)
public class SupportedGameVersion {
    @Id
    @Column(name = "plugin_id", nullable = false, updatable = false)
    private Long pluginId;
    
    @Id
    @Column(name = "plugin_version_title", nullable = false, length = 20)
    private String pluginVersionTitle;
    
    @Id
    @Column(name = "game_version_id", nullable = false, updatable = false)
    private Short gameVersionId;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SupportedGameVersion that = (SupportedGameVersion) o;

        if (pluginId != null ? !pluginId.equals(that.pluginId) : that.pluginId != null) return false;
        if (pluginVersionTitle != null ? !pluginVersionTitle.equals(that.pluginVersionTitle) : that.pluginVersionTitle != null)
            return false;
        if (gameVersionId != null ? !gameVersionId.equals(that.gameVersionId) : that.gameVersionId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pluginId != null ? pluginId.hashCode() : 0;
        result = 31 * result + (pluginVersionTitle != null ? pluginVersionTitle.hashCode() : 0);
        result = 31 * result + (gameVersionId != null ? gameVersionId.hashCode() : 0);
        return result;
    }
}
