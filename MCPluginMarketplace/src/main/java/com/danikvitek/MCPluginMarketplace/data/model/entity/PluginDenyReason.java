package com.danikvitek.MCPluginMarketplace.data.model.entity;

import com.danikvitek.MCPluginMarketplace.data.model.embedded.PluginDenyReasonId;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@Table(name = "plugin_deny_reasons")
public final class PluginDenyReason {
    @NotNull
    @EmbeddedId
    private PluginDenyReasonId id;

    @NotBlank
    @NotEmpty
    @Length(max = 200)
    @NotNull
    @Setter
    @Column(name = "reason", length = 200)
    private String reason;
}