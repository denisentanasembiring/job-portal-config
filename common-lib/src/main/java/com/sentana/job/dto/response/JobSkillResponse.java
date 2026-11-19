package com.sentana.job.dto.response;

import com.sentana.job.domain.SkillCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobSkillResponse {

    private Long id;
    private String name;
    private String slug;
    private SkillCategory skillCategory;
    private Boolean active;
}
