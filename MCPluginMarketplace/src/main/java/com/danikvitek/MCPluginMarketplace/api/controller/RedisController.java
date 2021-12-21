//package com.danikvitek.MCPluginMarketplace.api.controller;
//
//import com.danikvitek.MCPluginMarketplace.api.dto.RedisDto;
//import com.danikvitek.MCPluginMarketplace.service.RedisService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.net.URI;
//
//@Slf4j
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/redis")
//public final class RedisController {
//    private final RedisService redisService;
//
//    @PostMapping
//    public @NotNull ResponseEntity<Void> setValue(@RequestBody @NotNull RedisDto redisDto) {
//        String location = "/redis/" + redisDto.getKey();
//        redisService.create(redisDto);
//        return ResponseEntity.created(URI.create(location)).build();
//    }
//
//    @GetMapping("/{key}")
//    public ResponseEntity<RedisDto> getValue(@PathVariable String key) {
//        try {
//            String value = redisService.get(key);
//            return ResponseEntity.ok(
//                    RedisDto.builder()
//                            .value(value)
//                            .key(key)
//                            .build()
//            );
//        } catch (IllegalArgumentException e) {
//            log.info("/redis/" + key + ":", e);
//            return ResponseEntity.notFound().build();
//        }
//    }
//}
