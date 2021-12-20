package com.danikvitek.MCPluginMarketplace.service;

import com.danikvitek.MCPluginMarketplace.api.dto.RedisDto;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import scala.util.Try;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class RedisService {
    private final StringRedisTemplate redisTemplate;

    public void create(@NotNull RedisDto redisDto) throws IllegalArgumentException {
        Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection()
                .set(
                        redisDto.getKey().getBytes(),
                        redisDto.getValue().getBytes()
                );
    }

    public String get(@NotNull String key) throws IllegalArgumentException {
        return new String(
                Try.apply(() -> Optional.ofNullable(
                                        Objects.requireNonNull(redisTemplate.getConnectionFactory())
                                                .getConnection()
                                                .get(key.getBytes())
                                )
                        )
                        .flatMap(value ->
                                Try.apply(() ->
                                        value.orElseThrow(() -> new IllegalArgumentException("Key not found"))
                                )
                        )
                        .get(),
                StandardCharsets.UTF_8
        );
    }
}
