package com.danikvitek.MCPluginMarketplace.data.repository;

import com.danikvitek.MCPluginMarketplace.data.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByTitle(String title);
    
    @Query("select t from Tag t join PluginTag pt on t.id = pt.tagId and pt.pluginId = ?1")
    Set<Tag> showForPlugin(long pluginId);
}