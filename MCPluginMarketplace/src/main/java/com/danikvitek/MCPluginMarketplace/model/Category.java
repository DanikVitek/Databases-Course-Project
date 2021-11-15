package com.danikvitek.MCPluginMarketplace.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "categories")
public class Category {
    @Getter
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Length(max = 30)
    @NotNull
    @Setter
    @Getter
    @Column(name = "title", nullable = false, unique = true, length = 30)
    private String title;
}
