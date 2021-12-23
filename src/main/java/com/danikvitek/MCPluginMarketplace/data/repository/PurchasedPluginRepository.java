package com.danikvitek.MCPluginMarketplace.data.repository;

import com.danikvitek.MCPluginMarketplace.data.model.entity.PurchasedPlugin;
import com.danikvitek.MCPluginMarketplace.data.model.entity.PurchasedPluginPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchasedPluginRepository extends JpaRepository<PurchasedPlugin, PurchasedPluginPK> {
}