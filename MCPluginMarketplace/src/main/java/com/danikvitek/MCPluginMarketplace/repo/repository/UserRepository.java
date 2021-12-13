package com.danikvitek.MCPluginMarketplace.repo.repository;

import com.danikvitek.MCPluginMarketplace.repo.model.entity.Plugin;
import com.danikvitek.MCPluginMarketplace.repo.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
//    @Query("select u from User u where u.authoredPlugins = ?1")
//    Set<User> findByAuthoredPlugin(Plugin plugin);
    
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}