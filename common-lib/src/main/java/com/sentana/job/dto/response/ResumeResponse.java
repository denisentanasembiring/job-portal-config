package com.sentana.job.dto.response;

import com.sentana.job.domain.ResumeTemplate;
import com.sentana.job.domain.ResumeVisibility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeResponse {

    private Long id;
    private Long candidateId;
    private String title;
    private ResumeTemplate resumeTemplate;
    private ResumeVisibility resumeVisibility;
    private Boolean isDefault;
    private PersonalInformationResponse personalInformationResponse;
    private String summary;
    private Integer completionScore;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<WorkExperienceResponse> experienceResponses;
    private List<EducationResponse> educationResponses;
    private List<ResumeSkillResponse> resumeSkillResponses;
    private List<ProjectResponse> projectResponses;
    private List<LanguageResponse> languageResponses;
//    private List<CertificationResponse> certificationResponses;
//    private List<AwardResponse> awardResponses;

}
