package com.danikvitek.MCPluginMarketplace.data.model.entity;

import com.danikvitek.MCPluginMarketplace.data.model.embedded.PluginAuthorId;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "plugin_authors")
public class PluginAuthor {
    @EmbeddedId
    private PluginAuthorId id;

    public PluginAuthorId getId() {
        return id;
    }

    public void setId(PluginAuthorId id) {
        this.id = id;
    }
}