package com.danikvitek.MCPluginMarketplace.controllers;

import com.danikvitek.MCPluginMarketplace.model.Category;
import com.danikvitek.MCPluginMarketplace.model.Plugin;
import com.danikvitek.MCPluginMarketplace.services.PluginService;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PluginController {

    @Getter
    private final PluginService pluginService;

    public PluginController(PluginService pluginService) {
        this.pluginService = pluginService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        return new ResponseEntity<>(pluginService.getCategories(), HttpStatus.OK);
    }

    @GetMapping(value = "/plugins", params = {"size", "page"/*, "is_page_like=false"*/})
    public ResponseEntity<List<Plugin>> getPluginsList(int size, int page) {
        return new ResponseEntity<>(pluginService.getPlugins(size, page).toList(), HttpStatus.OK);
    }

    @GetMapping(value = "/plugins", params = {"size", "page", "is_page_like=true"})
    public ResponseEntity<Page<Plugin>> getPluginsPage(int size, int page) {
        return new ResponseEntity<>(pluginService.getPlugins(size, page), HttpStatus.OK);
    }
}
