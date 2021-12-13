package com.danikvitek.MCPluginMarketplace.repo.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.CodePointLength;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Entity
@Table(name = "banned_users")
public final class BannedUser {
    @Positive
    @NotNull
    @Id
    @Column(name = "user_id", nullable = false)
    private Long id;

    @NotBlank
    @NotEmpty
    @Length(max = 200)
    @NotNull
    @Setter
    @Column(name = "reason", length = 200)
    private String reason;
}