package com.sentana.job.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {

    @NotBlank(message = "project title is required")
    private String title;
    private String description;
    private List<String> technologies;

    @Pattern(regexp = "^(https?://).*", message = "project url must be valid")
    private String projectUrl;

    @Pattern(regexp = "^(https?://).*", message = "source code url must be valid")
    private String sourceUrl;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder.Default
    private Boolean isOnGoing = false;
    private Integer displayOrder;
}
