package com.danikvitek.MCPluginMarketplace.api.dto;

import com.danikvitek.MCPluginMarketplace.data.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

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

    private Timestamp registrationTime;
    
    @Builder.Default
    @PositiveOrZero
    @Digits(integer = 9, fraction = 2)
    @NumberFormat(pattern = "$###,###,###.00")
    private BigDecimal balance = BigDecimal.ZERO;
}
