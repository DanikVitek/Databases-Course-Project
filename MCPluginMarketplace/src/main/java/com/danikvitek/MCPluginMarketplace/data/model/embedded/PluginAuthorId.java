package com.danikvitek.MCPluginMarketplace.data.model.embedded;

import lombok.Getter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Embeddable
public class PluginAuthorId implements Serializable {
    private static final long serialVersionUID = 6653278048680680927L;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "plugin_id", nullable = false)
    private Long pluginId;

    @Override
    public int hashCode() {
        return Objects.hash(pluginId, userId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PluginAuthorId entity = (PluginAuthorId) o;
        return Objects.equals(this.pluginId, entity.pluginId) &&
                Objects.equals(this.userId, entity.userId);
    }
}