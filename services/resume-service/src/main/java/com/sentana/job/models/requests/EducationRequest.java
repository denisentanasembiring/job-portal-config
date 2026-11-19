package com.sentana.job.models.requests;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationRequest {

    @NotBlank(message = "institution is required")
    private String institutionName;

    @NotBlank(message = "degree is required")
    private String degree;

    private String fieldOfStudy;

    private String grade;

    @NotBlank(message = "start date is required")
    private LocalDate startDate;

    private LocalDate endDate;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isCurrentlyStudy=false;

    private String description;

    private Integer displayOrder;
}
