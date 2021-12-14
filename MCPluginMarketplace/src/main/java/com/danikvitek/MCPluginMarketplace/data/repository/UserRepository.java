package com.danikvitek.MCPluginMarketplace.data.repository;

import com.danikvitek.MCPluginMarketplace.data.model.entity.Plugin;
import com.danikvitek.MCPluginMarketplace.data.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u join PluginAuthor pa on pa.id.userId = u.id where pa.id.pluginId = ?1")
    Set<User> findByAuthoredPlugin(long pluginId);
    
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}