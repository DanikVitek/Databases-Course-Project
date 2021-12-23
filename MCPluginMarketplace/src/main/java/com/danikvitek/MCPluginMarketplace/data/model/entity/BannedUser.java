package com.danikvitek.MCPluginMarketplace.data.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "banned_users", schema = "course_project")
public class BannedUser {
    @Id
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    @NotBlank
    @Column(name = "reason", length = 200)
    private String reason;

    @Column(name = "ban_time", nullable = false, updatable = false)
    private Timestamp banTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BannedUser that = (BannedUser) o;

        if (!Objects.equals(userId, that.userId)) return false;
        if (!Objects.equals(reason, that.reason)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (reason != null ? reason.hashCode() : 0);
        return result;
    }
}
