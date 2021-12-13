package com.danikvitek.MCPluginMarketplace.services;

import com.danikvitek.MCPluginMarketplace.model.Category;
import com.danikvitek.MCPluginMarketplace.model.Plugin;
import com.danikvitek.MCPluginMarketplace.repositories.CategoryRepository;
import com.danikvitek.MCPluginMarketplace.repositories.PluginRepository;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PluginService {
    @Getter
    private final CategoryRepository categoryRepository;

    @Getter
    private final PluginRepository pluginRepository;

    public PluginService(CategoryRepository categoryRepository,
                         PluginRepository pluginRepository) {
        this.categoryRepository = categoryRepository;
        this.pluginRepository = pluginRepository;
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Page<Plugin> getPlugins(int size, int page) {
        return pluginRepository.findAll(PageRequest.of(page, size));
    }
}
