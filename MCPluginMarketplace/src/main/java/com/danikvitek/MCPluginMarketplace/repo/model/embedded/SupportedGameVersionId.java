package com.danikvitek.MCPluginMarketplace.repo.model.embedded;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Embeddable
public final class SupportedGameVersionId implements Serializable {
    private static final long serialVersionUID = -991473708714602776L;
    
    @Positive
    @NotNull
    @Column(name = "plugin_id", nullable = false, updatable = false)
    private Long pluginId;
    
    @NotBlank
    @NotEmpty
    @Length(max = 20)
    @NotNull
    @Setter
    @Column(name = "plugin_version_title", nullable = false, length = 20)
    private String pluginVersionTitle;
    
    @NotNull
    @Positive
    @Column(name = "game_version_id", nullable = false, updatable = false)
    private Integer gameVersionId;

    @Override
    public int hashCode() {
        return Objects.hash(pluginVersionTitle, gameVersionId, pluginId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SupportedGameVersionId entity = (SupportedGameVersionId) o;
        return Objects.equals(this.pluginVersionTitle, entity.pluginVersionTitle) &&
                Objects.equals(this.gameVersionId, entity.gameVersionId) &&
                Objects.equals(this.pluginId, entity.pluginId);
    }
}