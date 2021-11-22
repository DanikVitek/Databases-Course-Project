package com.danikvitek.MCPluginMarketplace.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Entity(name = "user")
@Table(name = "users")
public class User {
    @Getter
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "nickname", nullable = false, unique = true, length = 50)
    private String nickname;

    @Setter
    @Getter
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Setter
    @Getter
    @Column(name = "last_name", length = 50)
    private String lastName;

    @NotEmpty
    @NotBlank
    @NotNull
    @Email
    @Column(name = "email", nullable = false, unique = true)
    @Setter
    @Getter
    private String email;

    @Getter
    @Column(name = "password")
    @NotNull
    @Setter
    private String password;

    @Enumerated
    @Column(name = "role", nullable = false)
    @NotNull
    @Setter
    @Getter
    private Role role;

    @NotNull
    @Getter
    @Column(name = "registration_time", nullable = false)
    private Timestamp registrationTime;

    @Getter
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "plugin_authors",
            joinColumns = @JoinColumn(
                    name = "plugin_id",
                    referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "plugins_fk")),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "author_users_fk"))
    )
    private List<User> authors;

    public enum Role {
        user,
        moderator,
        admin
    }
}
