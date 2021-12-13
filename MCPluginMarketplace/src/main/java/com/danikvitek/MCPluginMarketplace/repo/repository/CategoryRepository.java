package com.danikvitek.MCPluginMarketplace.repo.repository;

import com.danikvitek.MCPluginMarketplace.repo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {}