package com.danikvitek.MCPluginMarketplace.data.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "plugins", schema = "course_project")
@Getter
@Setter
public class Plugin {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    
    @Basic
    @Column(name = "title", nullable = false, length = 200)
    private String title;
    
    @Lob
    @Column(name = "description")
    private String description;
    
    @Basic
    @Column(name = "category_id", nullable = true)
    private Short categoryId;
    
    @Basic
    @Column(name = "icon", nullable = true)
    private byte[] icon;
    
    @PositiveOrZero
    @Digits(integer = 5, fraction = 2)
    @Column(name = "price", nullable = false, precision = 2)
    private BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Plugin plugin = (Plugin) o;

        if (id != null ? !id.equals(plugin.id) : plugin.id != null) return false;
        if (title != null ? !title.equals(plugin.title) : plugin.title != null) return false;
        if (description != null ? !description.equals(plugin.description) : plugin.description != null) return false;
        if (categoryId != null ? !categoryId.equals(plugin.categoryId) : plugin.categoryId != null) return false;
        if (!Arrays.equals(icon, plugin.icon)) return false;
        if (price != null ? !price.equals(plugin.price) : plugin.price != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(icon);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
