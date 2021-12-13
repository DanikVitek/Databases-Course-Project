package com.danikvitek.MCPluginMarketplace.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class CategoryDto implements Serializable {
    @NotBlank
    @NotEmpty
    @NotNull
    @Length(max = 30)
    private String title;
}
