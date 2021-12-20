package com.danikvitek.MCPluginMarketplace.api.controller;

import com.danikvitek.MCPluginMarketplace.api.dto.RatingDto;
import com.danikvitek.MCPluginMarketplace.data.model.entity.PluginRating;
import com.danikvitek.MCPluginMarketplace.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rating")
public final class RatingController {
    private final RatingService ratingService;

    @GetMapping(params = {"plugin_id", "user_id"})
    public @NotNull ResponseEntity<RatingDto> showForPlugin(@RequestParam(name = "plugin_id") long pluginId,
                                                         @RequestParam(name = "user_id") long userId) {
        return ResponseEntity.ok(ratingService.ratingToDto(ratingService.fetchById(pluginId, userId)));
    }

    @PostMapping
    public @NotNull ResponseEntity<Void> create(@Valid @RequestBody RatingDto ratingDto) {
        PluginRating rating = ratingService.create(ratingDto);
        String location = String.format(
                "/rating?plugin_id=%d&user_id=%d",
                rating.getPluginId(), rating.getUserId());
        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping(params = {"plugin_id", "user_id"})
    public @NotNull ResponseEntity<Void> update(@RequestParam(name = "plugin_id") long pluginId,
                                                @RequestParam(name = "user_id") long userId,
                                                @Valid @RequestBody RatingDto ratingDto) {
        PluginRating rating = ratingService.update(pluginId, userId, ratingDto);
        String location = String.format(
                "/rating?plugin_id=%d&user_id=%d",
                rating.getPluginId(), rating.getUserId());
        return ResponseEntity.created(URI.create(location)).build();
    }
}
