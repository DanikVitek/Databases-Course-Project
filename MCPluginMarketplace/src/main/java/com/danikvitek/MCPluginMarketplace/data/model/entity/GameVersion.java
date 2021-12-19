package com.danikvitek.MCPluginMarketplace.data.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "game_versions", schema = "course_project")
public class GameVersion {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private Short id;
    
    @Basic
    @Column(name = "version_title", nullable = false, length = 20)
    private String versionTitle;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameVersion that = (GameVersion) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (versionTitle != null ? !versionTitle.equals(that.versionTitle) : that.versionTitle != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (versionTitle != null ? versionTitle.hashCode() : 0);
        return result;
    }
}
