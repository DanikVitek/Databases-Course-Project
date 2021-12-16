package com.danikvitek.MCPluginMarketplace.data.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PluginVersionPK implements Serializable {
    @Column(name = "plugin_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pluginId;
    @Column(name = "version_title", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String versionTitle;

    public Long getPluginId() {
        return pluginId;
    }

    public void setPluginId(Long pluginId) {
        this.pluginId = pluginId;
    }

    public String getVersionTitle() {
        return versionTitle;
    }

    public void setVersionTitle(String versionTitle) {
        this.versionTitle = versionTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PluginVersionPK that = (PluginVersionPK) o;

        if (pluginId != null ? !pluginId.equals(that.pluginId) : that.pluginId != null) return false;
        if (versionTitle != null ? !versionTitle.equals(that.versionTitle) : that.versionTitle != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pluginId != null ? pluginId.hashCode() : 0;
        result = 31 * result + (versionTitle != null ? versionTitle.hashCode() : 0);
        return result;
    }
}
