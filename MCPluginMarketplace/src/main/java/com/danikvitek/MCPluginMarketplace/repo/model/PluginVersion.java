package com.danikvitek.MCPluginMarketplace.repo.model;

import com.danikvitek.MCPluginMarketplace.repo.model.embedded.PluginVersionId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Blob;
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
    @Enumerated
    @Column(name = "upload_state", nullable = false)
    private UploadState uploadState = UploadState.PROCESSING;

    @NotNull
    @Setter
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "file", nullable = false)
    private Blob file;

    @NotNull
    @Column(name = "upload_time", nullable = false, updatable = false)
    private Timestamp uploadTime;

    @RequiredArgsConstructor
    @Getter
    @ToString
    public enum UploadState {
        PROCESSING((byte) 0, "Processing"),
        ACCEPTED((byte) 1, "Accepted"),
        DENIED((byte) 2, "Denied");
        
        private final byte id;
        private final String value;
    }
}