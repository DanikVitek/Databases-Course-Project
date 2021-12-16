package com.danikvitek.MCPluginMarketplace.data.repository;

import com.danikvitek.MCPluginMarketplace.data.model.entity.PluginTag;
import com.danikvitek.MCPluginMarketplace.data.model.entity.PluginTagPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PluginTagRepository extends JpaRepository<PluginTag, PluginTagPK> {
}
