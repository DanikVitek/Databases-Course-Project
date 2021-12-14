package com.danikvitek.MCPluginMarketplace.data.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.Instant;

@Getter
@Entity
@Table(name = "comments")
public class Comment {
    @Positive
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "plugin_id", nullable = false, updatable = false)
    private Plugin plugin;

    @NotBlank
    @Setter
    @Column(name = "content", nullable = false, length = 300)
    private String content;

    @Column(name = "publication_time", nullable = false, updatable = false)
    private Instant publicationTime;
}