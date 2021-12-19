package com.danikvitek.MCPluginMarketplace.data.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PurchasedPluginPK implements Serializable {
    @Column(name = "user_id", nullable = false, updatable = false)
    @Id
    private Long userId;
    
    @Column(name = "plugin_id", nullable = false, updatable = false)
    @Id
    private Long pluginId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchasedPluginPK that = (PurchasedPluginPK) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (pluginId != null ? !pluginId.equals(that.pluginId) : that.pluginId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (pluginId != null ? pluginId.hashCode() : 0);
        return result;
    }
}
