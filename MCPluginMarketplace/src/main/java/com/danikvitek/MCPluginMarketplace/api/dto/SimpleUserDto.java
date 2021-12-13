package com.danikvitek.MCPluginMarketplace.api.dto;

import com.danikvitek.MCPluginMarketplace.repo.model.entity.Role;
import com.danikvitek.MCPluginMarketplace.repo.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.NotNull;

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
    private String role = Role.user.toString();

    public static SimpleUserDto mapFromUser(@NotNull User user) {
        return SimpleUserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .build();
    }
}
