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
public class SupportedGameVersionPK implements Serializable {
    @Column(name = "plugin_id", nullable = false, updatable = false)
    @Id
    private Long pluginId;
    
    @Column(name = "plugin_version_title", nullable = false, length = 20)
    @Id
    private String pluginVersionTitle;
    
    @Column(name = "game_version_id", nullable = false, updatable = false)
    @Id
    private Short gameVersionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SupportedGameVersionPK that = (SupportedGameVersionPK) o;

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
