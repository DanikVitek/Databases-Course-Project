package com.danikvitek.MCPluginMarketplace.repo.model.embedded;

import lombok.AccessLevel;
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
public final class PluginVersionId implements Serializable {
    private static final long serialVersionUID = 8502629269769442627L;
    
    @Positive
    @NotNull
    @Column(name = "plugin_id", nullable = false, updatable = false)
    private Long pluginId;
    
    @Setter
    @NotBlank
    @NotEmpty
    @Length(max = 20)
    @NotNull
    @Column(name = "version_title", nullable = false, length = 20)
    private String versionTitle;

    @Override
    public int hashCode() {
        return Objects.hash(versionTitle, pluginId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PluginVersionId entity = (PluginVersionId) o;
        return Objects.equals(this.versionTitle, entity.versionTitle) &&
                Objects.equals(this.pluginId, entity.pluginId);
    }
}