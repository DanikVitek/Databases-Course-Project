package com.danikvitek.MCPluginMarketplace.data.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "nickname", columnList = "username", unique = true),
        @Index(name = "email", columnList = "email", unique = true)
})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public final class User {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "email", nullable = false, length = 254)
    private String email;

    @Column(name = "password", nullable = false, length = 1000)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Setter(AccessLevel.NONE)
    @Column(name = "registration_time", nullable = false, updatable = false)
    private Instant registrationTime;
    
    @ManyToMany(mappedBy = "authors")
    private Set<Plugin> authoredPlugins;
}