package com.danikvitek.MCPluginMarketplace.repo.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.Instant;

@Getter
@Entity
@Table(name = "users", indexes = {
        @Index(name = "nickname", columnList = "nickname", unique = true),
        @Index(name = "email", columnList = "email", unique = true)
})
public final class User {
    @Positive
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotEmpty
    @NotBlank
    @Length(max = 50)
    @Setter
    @Column(name = "nickname", nullable = false, unique = true, length = 50)
    private String username;

    @NotBlank
    @NotEmpty
    @Length(max = 50)
    @NotNull
    @Setter
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotBlank
    @NotEmpty
    @Length(max = 50)
    @NotNull
    @Setter
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @NotBlank
    @NotEmpty
    @NotNull
    @Length(max = 254)
    @Email
    @Setter
    @Column(name = "email", nullable = false, unique = true, length = 254)
    private String email;

    @NotEmpty
    @NotBlank
    @NotNull
    @Setter
    @Column(name = "password", nullable = false, length = 1000)
    private String password;

    @NotNull
    @Setter
    @Lob
    @Enumerated
    @Column(name = "role", nullable = false)
    private Role role = Role.USER;

    @NotNull
    @Column(name = "registration_time", nullable = false, updatable = false)
    private Instant registrationTime;
    
    @RequiredArgsConstructor
    @Getter
    @ToString
    public enum Role {
        USER((byte) 0, "user"),
        MODERATOR((byte) 1, "moderator"),
        ADMIN((byte) 2, "admin");
        
        private final byte id;
        private final String value;
    }
}