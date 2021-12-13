package com.danikvitek.MCPluginMarketplace.repo.model;

import com.danikvitek.MCPluginMarketplace.repo.model.embedded.SupportedGameVersionId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "supported_game_versions")
public final class SupportedGameVersion {
    @NotNull
    @Getter
    @Setter
    @EmbeddedId
    private SupportedGameVersionId id;
}