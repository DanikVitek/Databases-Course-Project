package com.danikvitek.MCPluginMarketplace.api.dto;

import com.danikvitek.MCPluginMarketplace.repo.model.entity.Plugin;
import com.danikvitek.MCPluginMarketplace.repo.model.entity.Tag;
import com.danikvitek.MCPluginMarketplace.repo.repository.TagRepository;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class PluginDto implements Serializable {
    @Positive
    private Long id;

    @NotBlank
    @Length(max = 200)
    private String title;

    @Length(min = 20)
    @NotBlank
    private String description;

    @Length(max = 30)
    @NotBlank
    private String categoryTitle;

    private byte[] icon;

    @Digits(integer = 5, fraction = 2)
    @PositiveOrZero
    @Builder.Default
    private BigDecimal price = BigDecimal.valueOf(0);

    private Set<String> authors;

    private Set<String> tags;

    public static PluginDto mapFromPlugin(@org.jetbrains.annotations.NotNull Plugin plugin) {
        return PluginDto.builder()
                .id(plugin.getId())
                .title(plugin.getTitle())
                .description(plugin.getDescription())
                .categoryTitle(plugin.getCategory().getTitle())
                .icon(plugin.getIcon())
                .price(plugin.getPrice())
                .tags(plugin.getTags().stream().map(Tag::getTitle).collect(Collectors.toSet()))
                .build();
    }

    public Plugin mapToPlugin() {
        return mapToPlugin(true);
    }
    
    public Plugin mapToPlugin(boolean includeId) {
        Plugin.PluginBuilder builder = Plugin.builder();
        if (includeId) builder.id(id);
        return builder.title(title)
                .description(description)
                .icon(icon)
                .price(price)
                .build();
    }
}
