package com.danikvitek.MCPluginMarketplace.api.dto;

import com.danikvitek.MCPluginMarketplace.repo.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimpleUserDto implements Serializable {
    @NotEmpty
    @NotBlank
    @Length(max = 50)
    private String username;
    
    @NotBlank
    @NotEmpty
    @Length(max = 50)
    @NotNull
    private String firstName;
    
    @NotBlank
    @NotEmpty
    @Length(max = 50)
    @NotNull
    private String lastName;
    
    @NotBlank
    @NotEmpty
    @NotNull
    @Length(max = 254)
    @Email
    private String email;
    
    @NotNull
    @Builder.Default
    private User.Role role = User.Role.user;
}
