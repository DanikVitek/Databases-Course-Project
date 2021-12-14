package com.danikvitek.MCPluginMarketplace.data.model.entity;

import com.danikvitek.MCPluginMarketplace.data.model.embedded.PluginRatingId;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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