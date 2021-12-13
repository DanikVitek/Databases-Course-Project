package com.danikvitek.MCPluginMarketplace.repo.model.entity;

import com.danikvitek.MCPluginMarketplace.repo.model.embedded.PluginTagId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "plugin_tags")
public final class PluginTag {
    @NotNull
    @Getter
    @Setter
    @EmbeddedId
    private PluginTagId id;
}