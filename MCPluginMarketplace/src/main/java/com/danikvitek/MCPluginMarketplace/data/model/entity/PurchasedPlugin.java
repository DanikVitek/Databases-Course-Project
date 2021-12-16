package com.danikvitek.MCPluginMarketplace.data.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "purchased_plugins", schema = "course_project")
@IdClass(PurchasedPluginPK.class)
public class PurchasedPlugin {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "plugin_id", nullable = false)
    private Long pluginId;
    @Basic
    @Column(name = "purchase_time", nullable = false)
    private Timestamp purchaseTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPluginId() {
        return pluginId;
    }

    public void setPluginId(Long pluginId) {
        this.pluginId = pluginId;
    }

    public Timestamp getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Timestamp purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchasedPlugin that = (PurchasedPlugin) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (pluginId != null ? !pluginId.equals(that.pluginId) : that.pluginId != null) return false;
        if (purchaseTime != null ? !purchaseTime.equals(that.purchaseTime) : that.purchaseTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (pluginId != null ? pluginId.hashCode() : 0);
        result = 31 * result + (purchaseTime != null ? purchaseTime.hashCode() : 0);
        return result;
    }
}
