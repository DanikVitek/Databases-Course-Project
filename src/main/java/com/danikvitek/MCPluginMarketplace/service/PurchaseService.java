package com.danikvitek.MCPluginMarketplace.service;

import com.danikvitek.MCPluginMarketplace.data.repository.PluginRepository;
import com.danikvitek.MCPluginMarketplace.data.repository.PurchasedPluginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public final class PurchaseService {
    private final PluginRepository pluginRepository;
    private final PurchasedPluginRepository purchasedPluginRepository;
    
    private final PluginService pluginService;
    private final UserService userService;
    
    // todo: implement
}
