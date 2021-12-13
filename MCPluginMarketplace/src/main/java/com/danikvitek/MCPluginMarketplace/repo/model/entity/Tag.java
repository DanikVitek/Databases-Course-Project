package com.danikvitek.MCPluginMarketplace.repo.model.entity;

import com.danikvitek.MCPluginMarketplace.api.dto.TagDto;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Entity
@Table(name = "tags", indexes = {
        @Index(name = "title", columnList = "title", unique = true)
})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class Tag {
    @Positive
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    
    @NotEmpty
    @NotBlank
    @Length(min = 1, max = 30)
    @NotNull
    @Setter
    @Column(name = "title", nullable = false, length = 30)
    private String title;

    public TagDto mapToDto() {
        return TagDto.builder()
                .id(id)
                .title(title)
                .build();
    }
}