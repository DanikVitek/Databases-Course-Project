package com.danikvitek.MCPluginMarketplace.repo.model.embedded;

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
public final class PurchasedPluginId implements Serializable {
    private static final long serialVersionUID = 3006762351149295906L;
    
    @Positive
    @NotNull
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;
    
    @Positive
    @NotNull
    @Column(name = "plugin_id", nullable = false, updatable = false)
    private Long pluginId;

    @Override
    public int hashCode() {
        return Objects.hash(pluginId, userId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PurchasedPluginId entity = (PurchasedPluginId) o;
        return Objects.equals(this.pluginId, entity.pluginId) &&
                Objects.equals(this.userId, entity.userId);
    }
}