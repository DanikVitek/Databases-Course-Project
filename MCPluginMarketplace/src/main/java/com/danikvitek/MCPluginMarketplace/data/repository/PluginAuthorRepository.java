package com.danikvitek.MCPluginMarketplace.data.repository;

import com.danikvitek.MCPluginMarketplace.data.model.entity.PluginAuthor;
import com.danikvitek.MCPluginMarketplace.data.model.entity.PluginAuthorPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PluginAuthorRepository extends JpaRepository<PluginAuthor, PluginAuthorPK> {
}
