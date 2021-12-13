package com.danikvitek.MCPluginMarketplace.repo.repository;

import com.danikvitek.MCPluginMarketplace.repo.model.entity.Plugin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PluginRepository extends JpaRepository<Plugin, Long> {
//    @Query("select p from Plugin p where p.authors = ?1")
//    Set<Plugin> findAuthoredPlugins(User authors);
}