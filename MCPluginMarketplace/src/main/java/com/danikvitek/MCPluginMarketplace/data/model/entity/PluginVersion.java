package com.danikvitek.MCPluginMarketplace.data.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "plugin_versions", schema = "course_project")
@IdClass(PluginVersionPK.class)
public class PluginVersion {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "plugin_id", nullable = false)
    private Long pluginId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "version_title", nullable = false, length = 20)
    private String versionTitle;
    @Enumerated(EnumType.STRING)
    @Column(name = "upload_state", nullable = false)
    @Builder.Default
    private UploadState uploadState = UploadState.Processing;
    @Basic
    @Column(name = "file", nullable = false)
    private byte[] file;
    @Basic
    @Column(name = "upload_time", nullable = false)
    private Timestamp uploadTime;

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

    public UploadState getUploadState() {
        return uploadState;
    }

    public void setUploadState(UploadState uploadState) {
        this.uploadState = uploadState;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PluginVersion that = (PluginVersion) o;

        if (pluginId != null ? !pluginId.equals(that.pluginId) : that.pluginId != null) return false;
        if (versionTitle != null ? !versionTitle.equals(that.versionTitle) : that.versionTitle != null) return false;
        if (uploadState != null ? !uploadState.equals(that.uploadState) : that.uploadState != null) return false;
        if (!Arrays.equals(file, that.file)) return false;
        if (uploadTime != null ? !uploadTime.equals(that.uploadTime) : that.uploadTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pluginId != null ? pluginId.hashCode() : 0;
        result = 31 * result + (versionTitle != null ? versionTitle.hashCode() : 0);
        result = 31 * result + (uploadState != null ? uploadState.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(file);
        result = 31 * result + (uploadTime != null ? uploadTime.hashCode() : 0);
        return result;
    }
}
