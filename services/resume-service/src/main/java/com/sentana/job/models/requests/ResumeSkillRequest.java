package com.sentana.job.models.requests;

import com.sentana.job.domain.ProficiencyLevel;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeSkillRequest {

    @NotBlank(message = "skill name is required")
    @Size(max = 100, message = "skill name must not exceed 100 character")
    private String skillName;

    @NotNull(message = "proficiency level is required")
    private ProficiencyLevel proficiencyLevel;

    @Min(value = 1, message = "years of experience must not be negative")
    private Integer yearsOfExperience;


    private Integer displayOrder;
}
