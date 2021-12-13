package com.danikvitek.MCPluginMarketplace.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtRequestDto {
    String login;
    String password;
}
