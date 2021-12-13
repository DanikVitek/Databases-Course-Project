package com.danikvitek.MCPluginMarketplace.repo.model;

import com.danikvitek.MCPluginMarketplace.repo.model.embedded.PluginRatingId;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Entity
@Table(name = "plugin_ratings")
public final class PluginRating {
    @NotNull
    @EmbeddedId
    private PluginRatingId id;

    @NotNull
    @Range(min = 0, max = 5)
    @Setter
    @Column(name = "rating", nullable = false)
    private Integer rating;
}