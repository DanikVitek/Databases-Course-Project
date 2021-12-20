package com.danikvitek.MCPluginMarketplace.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingDto implements Serializable {
    @Positive
    private Long pluginId;
    @Positive
    private Long userId;
    @Min(value = 0L)
    @Max(value = 5L)
    private Byte rating;
}
