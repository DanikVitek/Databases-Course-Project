package com.danikvitek.MCPluginMarketplace.data.repository;

import com.danikvitek.MCPluginMarketplace.data.model.entity.PluginRating;
import com.danikvitek.MCPluginMarketplace.data.model.entity.PluginRatingPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PluginRatingRepository extends JpaRepository<PluginRating, PluginRatingPK> {
    @Query("select avg(pr.rating) from PluginRating pr join Plugin p on pr.pluginId = p.id and p.id = ?1")
    Optional<Double> getByPluginId(long pluginId);
}