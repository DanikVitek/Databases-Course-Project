package com.danikvitek.MCPluginMarketplace.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class PurchaseDto implements Serializable {
    private String username;
    private String pluginTitle;
    @PositiveOrZero
    @Digits(integer = 5, fraction = 2)
    private BigDecimal price;
    private Timestamp purchaseTime;
}
