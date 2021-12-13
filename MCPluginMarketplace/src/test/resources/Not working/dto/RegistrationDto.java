package com.danikvitek.MCPluginMarketplace.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegistrationDto {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
}