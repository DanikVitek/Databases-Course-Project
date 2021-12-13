package com.danikvitek.MCPluginMarketplace.repo.model.entity;

import com.danikvitek.MCPluginMarketplace.api.dto.FullUserDto;
import com.danikvitek.MCPluginMarketplace.api.dto.SimpleUserDto;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", indexes = {
        @Index(name = "username", columnList = "username", unique = true),
        @Index(name = "email", columnList = "email", unique = true)
})
public final class User implements Serializable {
    @Setter(AccessLevel.NONE)
    @Positive
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotBlank
    @Length(max = 50)
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @NotBlank
    @Length(max = 50)
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotBlank
    @Length(max = 50)
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @NotBlank
    @Length(max = 254)
    @Email
    @Column(name = "email", nullable = false, unique = true, length = 254)
    private String email;

    @NotBlank
    @ToString.Exclude
    @Column(name = "password", nullable = false, length = 1000)
    private String password;

    @NotNull
    @Lob
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role = Role.user;

    @Setter(AccessLevel.NONE)
    @Column(name = "registration_time", nullable = false, updatable = false)
    private Instant registrationTime;

    @ManyToMany(mappedBy = "authors")
    @ToString.Exclude
    private Set<Plugin> authoredPlugins = new LinkedHashSet<>();

    public void addAuthoredPlugin(Plugin plugin) {
        authoredPlugins.add(plugin);
        plugin.getAuthors().add(this);
    }

    public void removeAuthoredPlugin(Plugin plugin) {
        authoredPlugins.remove(plugin);
        plugin.getAuthors().remove(this);
    }

    public void removeAuthoredPlugins() {
        for (Plugin plugin: new LinkedHashSet<>(authoredPlugins))
            removeAuthoredPlugin(plugin);
    }

    public SimpleUserDto mapToSimpleDto() {
        return SimpleUserDto.builder()
                .id(id)
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .role(role.toString())
                .build();
    }

    public FullUserDto mapToFullDto() {
        return FullUserDto.builder()
                .id(id)
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .role(role.toString())
                .password(password)
                .registrationTime(registrationTime)
                .build();
    }

    @RequiredArgsConstructor
    @Getter
    @ToString
    public enum Role {
        user((byte) 0),
        moderator((byte) 1),
        admin((byte) 2);
        
        private final byte id;
    }
}