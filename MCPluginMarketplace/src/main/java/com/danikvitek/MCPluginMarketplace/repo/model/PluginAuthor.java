package com.danikvitek.MCPluginMarketplace.repo.model;

import com.danikvitek.MCPluginMarketplace.repo.model.embedded.PluginAuthorId;
import lombok.Getter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "plugin_authors")
public final class PluginAuthor {
    @NotNull
    @Getter
    @EmbeddedId
    private PluginAuthorId id;
}