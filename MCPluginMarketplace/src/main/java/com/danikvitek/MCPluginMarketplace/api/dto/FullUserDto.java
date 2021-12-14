package com.danikvitek.MCPluginMarketplace.api.dto;

import com.danikvitek.MCPluginMarketplace.data.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class FullUserDto implements Serializable {
    @Positive
    private Long id;
    
    @Length(max = 50)
    private String username;
    
    @Length(max = 50)
    private String firstName;
    
    @Length(max = 50)
    private String lastName;
    
    @Length(max = 254)
    @Email
    private String email;
    
    private String password;
    
    @NotNull
    @Builder.Default
    private String role = Role.user.toString();

    private Instant registrationTime;
}
