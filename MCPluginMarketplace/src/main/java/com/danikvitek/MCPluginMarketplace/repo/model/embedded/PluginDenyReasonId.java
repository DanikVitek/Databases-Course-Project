package com.danikvitek.MCPluginMarketplace.repo.model.embedded;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Embeddable
public final class PluginDenyReasonId implements Serializable {
    private static final long serialVersionUID = -1837770630178148649L;
    
    @Positive
    @NotNull
    @Column(name = "plugin_id", nullable = false, updatable = false)
    private Long pluginId;
    
    @NotBlank
    @NotEmpty
    @Length(max = 20)
    @NotNull
    @Setter
    @Column(name = "plugin_version", nullable = false, length = 20)
    private String pluginVersion;

    @Override
    public int hashCode() {
        return Objects.hash(pluginVersion, pluginId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PluginDenyReasonId entity = (PluginDenyReasonId) o;
        return Objects.equals(this.pluginVersion, entity.pluginVersion) &&
                Objects.equals(this.pluginId, entity.pluginId);
    }
}