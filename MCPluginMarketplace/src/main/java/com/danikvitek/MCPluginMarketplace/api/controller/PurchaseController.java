package com.danikvitek.MCPluginMarketplace.api.controller;

import com.danikvitek.MCPluginMarketplace.api.dto.PurchaseDto;
import com.danikvitek.MCPluginMarketplace.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("/purchases")
public final class PurchaseController {
    private final PurchaseService purchaseService;

    @GetMapping(params = {"user_id"})
    public @NotNull ResponseEntity<Collection<PurchaseDto>> showForUser(@RequestParam("user_id") long userId) {
        return ResponseEntity.noContent().build(); // todo: implement
    }

    @PostMapping
    public @NotNull ResponseEntity<Void> create(@Valid @RequestBody PurchaseDto purchaseDto) {
        return ResponseEntity.noContent().build(); // todo: implement
    }
}
