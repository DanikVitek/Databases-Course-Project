package com.danikvitek.MCPluginMarketplace.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity(name = "plugin")
@Table(name = "plugins")
public class Plugin {
    @Getter
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @Getter
    @Setter
    @Column(name = "title", nullable = false, unique = true, length = 200)
    private String title;

    @Column(name = "description")
    @Lob
    @Setter
    @Getter
    private String pluginDescription;

    @Column(name = "category_id", nullable = false)
    @NotNull
    @Setter
    @Getter
    private Short categoryId;

    @Setter
    @Getter
    @Lob
    @Column(name = "icon")
    private String icon;

//    @Getter
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "plugin_authors",
//            joinColumns = @JoinColumn(
//                    name = "user_id",
//                    referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "plugin_id",
//                    referencedColumnName = "id")
//    )
//    private List<User> authors;
}
