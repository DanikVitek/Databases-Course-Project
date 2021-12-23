package com.danikvitek.MCPluginMarketplace.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class CommentDto implements Serializable {
    private Long id;

    @Positive
    private Long userId;
    @Positive
    private Long pluginId;
    @NotBlank
    @Length(max = 300)
    private String content;

    private Timestamp publicationTime;
    private Collection<CommentDto> responses;
}
