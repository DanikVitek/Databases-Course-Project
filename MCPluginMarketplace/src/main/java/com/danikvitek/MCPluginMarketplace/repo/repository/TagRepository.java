package com.danikvitek.MCPluginMarketplace.repo.repository;

import com.danikvitek.MCPluginMarketplace.repo.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByTitle(String title);
}