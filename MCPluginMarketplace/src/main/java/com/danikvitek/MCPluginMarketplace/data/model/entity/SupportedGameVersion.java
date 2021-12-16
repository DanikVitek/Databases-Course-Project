package com.danikvitek.MCPluginMarketplace.data.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "supported_game_versions", schema = "course_project")
@IdClass(SupportedGameVersionPK.class)
public class SupportedGameVersion {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "plugin_id", nullable = false)
    private Long pluginId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "plugin_version_title", nullable = false, length = 20)
    private String pluginVersionTitle;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "game_version_id", nullable = false)
    private Short gameVersionId;

    public Long getPluginId() {
        return pluginId;
    }

    public void setPluginId(Long pluginId) {
        this.pluginId = pluginId;
    }

    public String getPluginVersionTitle() {
        return pluginVersionTitle;
    }

    public void setPluginVersionTitle(String pluginVersionTitle) {
        this.pluginVersionTitle = pluginVersionTitle;
    }

    public Short getGameVersionId() {
        return gameVersionId;
    }

    public void setGameVersionId(Short gameVersionId) {
        this.gameVersionId = gameVersionId;
    }

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