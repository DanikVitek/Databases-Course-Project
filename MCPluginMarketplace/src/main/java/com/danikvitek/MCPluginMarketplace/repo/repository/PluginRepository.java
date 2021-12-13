package com.danikvitek.MCPluginMarketplace.repo.repository;

import com.danikvitek.MCPluginMarketplace.repo.model.entity.Plugin;
import com.danikvitek.MCPluginMarketplace.repo.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface PluginRepository extends JpaRepository<Plugin, Long> {
//    @Query("select p from Plugin p where p.authors = ?1")
//    Set<Plugin> findAuthoredPlugins(User authors);
}