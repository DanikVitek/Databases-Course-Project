package com.danikvitek.MCPluginMarketplace.repo.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Entity
@Table(name = "plugins", indexes = {
        @Index(name = "title", columnList = "title", unique = true)
})
public final class Plugin {
    @Setter(AccessLevel.NONE)
    @Positive
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank
    @NotEmpty
    @Length(max = 200)
    @NotNull
    @Column(name = "title", nullable = false, unique = true, length = 200)
    private String title;

    @ToString.Exclude
    @Length(min = 20)
    @NotNull
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "description")
    private String description;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ToString.Exclude
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "icon")
    private byte[] icon;

    @NotNull
    @Digits(integer = 5, fraction = 2)
    @PositiveOrZero
    @Column(name = "price", nullable = false, precision = 5, scale = 2)
    private BigDecimal price = BigDecimal.valueOf(0);

//    @ManyToMany
//    @JoinTable(
//            name = "plugin_authors",
//            foreignKey = @ForeignKey(name = "plugin_id"),
//            inverseForeignKey = @ForeignKey(name = "user_id"),
//            joinColumns = @JoinColumn(name = "plugin_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id")
//    )
//    @ToString.Exclude
//    private Collection<User> authors;
}