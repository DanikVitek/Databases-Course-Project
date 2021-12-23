package com.danikvitek.MCPluginMarketplace.api.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

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
    @NotBlank
    private String description;

    @Length(max = 30)
    @NotBlank
    private String categoryTitle;

    private byte[] icon;

    @Digits(integer = 5, fraction = 2)
    @PositiveOrZero
    @Builder.Default
    private BigDecimal price = BigDecimal.valueOf(0);

    private Double rating;
    
    @Builder.Default
    private long ratingAmount = 0L;
    
    private Set<String> authors;
    
    private Set<String> tags;
}
