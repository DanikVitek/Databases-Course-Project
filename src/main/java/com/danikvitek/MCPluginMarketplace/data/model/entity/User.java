package com.danikvitek.MCPluginMarketplace.data.model.entity;

import lombok.*;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", schema = "course_project")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    
    @Basic
    @Column(name = "username", nullable = false, length = 50)
    private String username;
    
    @Basic
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    
    @Basic
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    
    @Email
    @Column(name = "email", nullable = false, length = 254)
    private String email;
    
    @Basic
    @Column(name = "password", nullable = false, length = 1000)
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @Builder.Default
    private Role role = Role.user;
    
    @Column(name = "registration_time", nullable = false, updatable = false)
    private Timestamp registrationTime;

    @PositiveOrZero
    @Digits(integer = 9, fraction = 2)
    @NumberFormat(pattern = "$###,###,###.00")
    @Builder.Default
    @Column(name = "balance", nullable = false, precision = 2, scale = 9)
    private BigDecimal balance = BigDecimal.ZERO;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!Objects.equals(id, user.id)) return false;
        if (!Objects.equals(username, user.username)) return false;
        if (!Objects.equals(firstName, user.firstName)) return false;
        if (!Objects.equals(lastName, user.lastName)) return false;
        if (!Objects.equals(email, user.email)) return false;
        if (!Objects.equals(password, user.password)) return false;
        if (!Objects.equals(role, user.role)) return false;
        if (!Objects.equals(registrationTime, user.registrationTime)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (registrationTime != null ? registrationTime.hashCode() : 0);
        return result;
    }
}
