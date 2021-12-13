package com.danikvitek.MCPluginMarketplace.repo.model;

import com.danikvitek.MCPluginMarketplace.repo.model.embedded.PluginAuthorId;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "plugin_authors")
public class PluginAuthor {
    @NotNull
    @EmbeddedId
    private PluginAuthorId id;

    public PluginAuthorId getId() {
        return id;
    }

    public void setId(PluginAuthorId id) {
        this.id = id;
    }
}