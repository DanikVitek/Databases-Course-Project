package com.danikvitek.MCPluginMarketplace.api.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class JwtRequestDto implements Serializable {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
}
