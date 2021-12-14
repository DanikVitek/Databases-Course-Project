package com.danikvitek.MCPluginMarketplace.data.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "game_versions", indexes = {
        @Index(name = "version_title", columnList = "version_title", unique = true)
})
public final class GameVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Setter
    @Column(name = "version_title", nullable = false, length = 20)
    private String versionTitle;
}