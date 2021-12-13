package com.danikvitek.MCPluginMarketplace.repo.model.entity;

import com.danikvitek.MCPluginMarketplace.repo.model.embedded.PluginVersionId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Entity
@Table(name = "plugin_versions")
public final class PluginVersion {
    @NotNull
    @EmbeddedId
    private PluginVersionId id;

    @NotNull
    @Setter
    @Lob
    @Enumerated(EnumType.STRING)
    @Column(name = "upload_state", nullable = false)
    private UploadState uploadState = UploadState.Processing;

    @NotNull
    @Setter
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "file", nullable = false)
    private byte[] file;

    @NotNull
    @Column(name = "upload_time", nullable = false, updatable = false)
    private Timestamp uploadTime;

    @RequiredArgsConstructor
    @Getter
    @ToString
    public enum UploadState {
        Processing((byte) 0),
        Accepted((byte) 1),
        Denied((byte) 2);
        
        private final byte id;
    }
}