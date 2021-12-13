package com.danikvitek.MCPluginMarketplace.repo.model.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tags", indexes = {
        @Index(name = "title", columnList = "title", unique = true)
})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public final class Tag {
    @Setter(AccessLevel.NONE)
    @Positive
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    
    @NotBlank
    @Length(min = 1, max = 30)
    @Column(name = "title", nullable = false, length = 30)
    private String title;

    @ManyToMany(mappedBy = "tags")
    @ToString.Exclude
    @Builder.Default
    private Set<Plugin> taggedPlugins = new LinkedHashSet<>();
}