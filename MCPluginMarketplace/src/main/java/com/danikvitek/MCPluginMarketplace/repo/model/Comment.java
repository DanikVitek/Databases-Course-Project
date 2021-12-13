package com.danikvitek.MCPluginMarketplace.repo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.sql.Timestamp;

@Getter
@Entity
@Table(name = "comments")
public final class Comment {
    @Positive
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    @NotEmpty
    @Length(max = 300)
    @NotNull
    @Setter
    @Column(name = "content", nullable = false, length = 300)
    private String content;

    @NotNull
    @Column(name = "publication_time", nullable = false, updatable = false)
    private Timestamp publicationTime;
}