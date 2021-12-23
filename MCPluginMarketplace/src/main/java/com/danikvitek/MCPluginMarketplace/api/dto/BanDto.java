package com.danikvitek.MCPluginMarketplace.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BanDto implements Serializable {
    @NotNull
    @Positive
    private Long userId;
    @NotBlank
    private String reason;
    @Builder.Default
    private Timestamp banTime = Timestamp.valueOf(LocalDateTime.now());
}
