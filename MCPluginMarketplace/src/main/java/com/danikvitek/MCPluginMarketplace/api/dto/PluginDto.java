package com.danikvitek.MCPluginMarketplace.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class PluginDto implements Serializable {
    @Positive
    private Long id;
    
    @NotBlank
    @Length(max = 200)
    private String title;

    @Length(min = 20)
    @NotNull
    private String description;

    @NotBlank
    @Length(max = 30)
    private String categoryTitle;

    private byte[] icon;

    @NotNull
    @Digits(integer = 5, fraction = 2)
    @PositiveOrZero
    private BigDecimal price = BigDecimal.valueOf(0);
}
