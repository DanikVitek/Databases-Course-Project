package com.danikvitek.MCPluginMarketplace.repo.repository;

import com.danikvitek.MCPluginMarketplace.repo.model.Plugin;
import com.danikvitek.MCPluginMarketplace.repo.model.PluginAuthor;
import com.danikvitek.MCPluginMarketplace.repo.model.User;
import com.danikvitek.MCPluginMarketplace.repo.model.embedded.PluginAuthorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PluginAuthorRepository extends JpaRepository<PluginAuthor, PluginAuthorId> {
    @Query("select p from Plugin p join PluginAuthor pa on p.id=pa.id.pluginId where pa.id.userId = ?1")
    List<Plugin> findAllById_UserId(Long userId);

    @Query("select u from User u join PluginAuthor pa on u.id=pa.id.userId where pa.id.pluginId = ?1")
    List<User> findAllById_PluginId(Long pluginId);
}
