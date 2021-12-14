package com.danikvitek.MCPluginMarketplace.data.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Entity
@Table(name = "categories", indexes = {
        @Index(name = "title", columnList = "title", unique = true)
})
public final class Category {
    @Positive
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;
    
    @NotBlank
    @NotEmpty
    @NotNull
    @Length(max = 30)
    @Setter
    @Column(name = "title", nullable = false, length = 30)
    private String title;
}