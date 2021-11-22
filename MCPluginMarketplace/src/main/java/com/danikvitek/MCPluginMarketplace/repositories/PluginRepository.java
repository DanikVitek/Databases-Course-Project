package com.danikvitek.MCPluginMarketplace.repositories;

import com.danikvitek.MCPluginMarketplace.model.Plugin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PluginRepository extends JpaRepository<Plugin, Long> {
}
