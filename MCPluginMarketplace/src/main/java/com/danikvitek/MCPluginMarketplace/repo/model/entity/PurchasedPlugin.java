package com.danikvitek.MCPluginMarketplace.repo.model.entity;

import com.danikvitek.MCPluginMarketplace.repo.model.embedded.PurchasedPluginId;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Entity
@Table(name = "purchased_plugins")
public final class PurchasedPlugin {
    @NotNull
    @EmbeddedId
    private PurchasedPluginId id;

    @NotNull
    @Column(name = "purchase_time", nullable = false, updatable = false)
    private Timestamp purchaseTime;
}