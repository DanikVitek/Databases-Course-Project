package com.danikvitek.MCPluginMarketplace.data.model.embedded;

import lombok.Getter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Embeddable
public final class PluginTagId implements Serializable {
    private static final long serialVersionUID = 5761601212093512219L;

    @Positive
    @NotNull
    @Column(name = "plugin_id", nullable = false, updatable = false)
    private Long pluginId;

    @Positive
    @NotNull
    @Column(name = "tag_id", nullable = false, updatable = false)
    private Long tagId;

    @Override
    public int hashCode() {
        return Objects.hash(tagId, pluginId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PluginTagId entity = (PluginTagId) o;
        return Objects.equals(this.tagId, entity.tagId) &&
                Objects.equals(this.pluginId, entity.pluginId);
    }
}