package com.sentana.job.models.requests;

import com.sentana.job.domain.LanguageProficiency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LanguageRequest {

    @NotBlank(message = "language name is required")
    private String languageName;

    @NotNull(message = "proficiency level is required")
    private LanguageProficiency proficiency;

    private Integer displayOrder;
}
