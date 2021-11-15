package com.danikvitek.MCPluginMarketplace.services;

import com.danikvitek.MCPluginMarketplace.model.Category;
import com.danikvitek.MCPluginMarketplace.repositories.CategoryRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Getter
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
}
