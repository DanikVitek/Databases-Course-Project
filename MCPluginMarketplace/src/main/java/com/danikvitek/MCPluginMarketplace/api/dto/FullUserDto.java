package com.danikvitek.MCPluginMarketplace.api.dto;

import com.danikvitek.MCPluginMarketplace.repo.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class FullUserDto implements Serializable {
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
    private String password;
    
    @NotNull
    @Builder.Default
    private String role = User.Role.user.toString();

    private Instant registrationTime;
    
    public SimpleUserDto mapToSimpleDto() {
        return SimpleUserDto.builder()
                .id(id)
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .role(role)
                .build();
    }
}
