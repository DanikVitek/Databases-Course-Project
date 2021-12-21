package com.danikvitek.MCPluginMarketplace.api.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class JwtResponseDto implements Serializable {
    private String token;
}
