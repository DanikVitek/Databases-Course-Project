package com.danikvitek.MCPluginMarketplace.api.dto;

import com.danikvitek.MCPluginMarketplace.repo.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class SimpleUserDto implements Serializable {
    @Positive
    private Long id;
    
    @NotBlank
    @Length(max = 50)
    private String username;
    
    @NotBlank
    @Length(max = 50)
    private String firstName;
    
    @NotBlank
    @Length(max = 50)
    private String lastName;
    
    @NotBlank
    @Length(max = 254)
    @Email
    private String email;
    
    @NotBlank
    @Builder.Default
    private String role = User.Role.user.toString();
}
