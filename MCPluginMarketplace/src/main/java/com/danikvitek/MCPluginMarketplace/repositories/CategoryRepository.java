package com.danikvitek.MCPluginMarketplace.repositories;

import com.danikvitek.MCPluginMarketplace.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Short> {}
