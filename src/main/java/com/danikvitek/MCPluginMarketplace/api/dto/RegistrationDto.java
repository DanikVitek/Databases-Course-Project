package com.danikvitek.MCPluginMarketplace.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class RegistrationDto implements Serializable {
    @NotBlank
    @Length(min = 3, max = 50)
    private String username;
    
    @Length(min = 8)
    private String password;

    @NotBlank
    @Length(min = 1, max = 50)
    private String firstName;

    @NotBlank
    @Length(min = 1, max = 50)
    private String lastName;

    @Email
    @NotBlank
    private String email;
}
