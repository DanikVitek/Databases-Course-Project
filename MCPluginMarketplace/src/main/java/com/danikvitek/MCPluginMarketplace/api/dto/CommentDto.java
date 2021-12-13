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
public class CommentDto implements Serializable {
    @NotNull
    private FullUserDto user;
    
    @NotBlank
    @NotEmpty
    @Length(max = 300)
    @NotNull
    private String content;
}
