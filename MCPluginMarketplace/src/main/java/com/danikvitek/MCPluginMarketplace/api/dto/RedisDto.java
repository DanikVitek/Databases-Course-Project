package com.danikvitek.MCPluginMarketplace.api.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Getter
public final class RedisDto implements Serializable {
    @NotBlank
    private String key;

    @NotBlank
    private String value;
}
