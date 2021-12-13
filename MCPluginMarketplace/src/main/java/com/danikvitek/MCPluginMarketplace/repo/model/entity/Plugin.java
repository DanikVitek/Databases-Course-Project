package com.danikvitek.MCPluginMarketplace.repo.model.entity;

import com.danikvitek.MCPluginMarketplace.api.dto.PluginDto;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plugins", indexes = {
        @Index(name = "title", columnList = "title", unique = true)
})
public final class Plugin implements Serializable {
    @Setter(AccessLevel.NONE)
    @Positive
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotBlank
    @Length(max = 200)
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

    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(
            name = "plugin_authors",
//            foreignKey = @ForeignKey(name = "plugin_id"),
//            inverseForeignKey = @ForeignKey(name = "user_id"),
            joinColumns = @JoinColumn(name = "plugin_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    @ToString.Exclude
    private Set<User> authors = new LinkedHashSet<>();

    public void addAuthor(User author) {
        authors.add(author);
        author.getAuthoredPlugins().add(this);
    }

    public void removeAuthor(User author) {
        authors.remove(author);
        author.getAuthoredPlugins().remove(this);
    }

    public void removeAuthors() {
        for (User author: new LinkedHashSet<>(authors))
            removeAuthor(author);
    }

    public PluginDto mapToDto() {
        return PluginDto.builder()
                .id(getId())
                .title(getTitle())
                .description(getDescription())
                .categoryTitle(getCategory().getTitle())
                .icon(getIcon())
                .price(getPrice())
                .build();
    }
}