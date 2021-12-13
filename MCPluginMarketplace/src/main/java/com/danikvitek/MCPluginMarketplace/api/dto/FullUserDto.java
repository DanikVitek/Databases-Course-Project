package com.danikvitek.MCPluginMarketplace.api.dto;

import com.danikvitek.MCPluginMarketplace.repo.model.entity.Role;
import com.danikvitek.MCPluginMarketplace.repo.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
    private String role = Role.user.toString();

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

    public static FullUserDto mapFromUser(@org.jetbrains.annotations.NotNull User user) {
        return FullUserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .password(user.getPassword())
                .registrationTime(user.getRegistrationTime())
                .build();
    }
    
    public User mapToUser() {
        return User.builder()
                .id(id)
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .email(email)
                .role(Role.valueOf(role))
                .registrationTime(registrationTime)
                .build();
    }
}
