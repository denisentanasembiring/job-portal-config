package com.sentana.job.mappers;

import com.sentana.job.dto.response.*;
import com.sentana.job.models.entities.*;

public class ResumeMapper {

    public static PersonalInformationResponse toPersonalInformationResponse(PersonalInformation info){
        return PersonalInformationResponse.builder()
                .firstname(info.getFirstname())
                .lastname(info.getLastname())
                .headline(info.getHeadline())
                .email(info.getEmail())
                .phoneNumber(info.getPhoneNumber())
                .city(info.getCity())
                .country(info.getCountry())
                .linkedinUrl(info.getLinkedinUrl())
                .githubUrl(info.getGithubUrl())
                .portfolioUrl(info.getPortfolioUrl())
                .websiteUrl(info.getWebsiteUrl())
                .build();
    }
    public static ResumeResponse toResponse(Resume resume){
        if (resume==null) return null;
        return ResumeResponse.builder()
                .id(resume.getId())
                .title(resume.getTitle())
                .resumeTemplate(resume.getTemplate())
                .resumeVisibility(resume.getVisibility())
                .isDefault(resume.getIsDefault())
                .personalInformationResponse(ResumeMapper.toPersonalInformationResponse(resume.getPersonalInformation()))
                .summary(resume.getSummary())
                .completionScore(resume.getCompletionScore())
                .createdAt(resume.getCreatedAt())
                .updatedAt(resume.getUpdatedAt())
                .build();
    }

    public static ResumeSkillResponse toSkillResponse(ResumeSkill skill){
        if (skill == null)return null;
        return ResumeSkillResponse.builder()
                .id(skill.getId())
                .skillName(skill.getSkillName())
                .proficiencyLevel(skill.getProficiencyLevel())
                .yearsOfExperience(skill.getYearOfExperience())
                .displayOrder(skill.getDisplayOrder())
                .build();
    }

    public static EducationResponse toEducationResponse(Education education){
        if (education == null) return null;
        return EducationResponse.builder()
                .id(education.getId())
                .institutionName(education.getInstitutionName())
                .degree(education.getDegree())
                .fieldOfStudy(education.getFieldOfStudy())
                .grade(education.getGrade()).
                startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .isCurrentlyStudy(education.getIsCurrentlyStudy())
                .description(education.getDescription())
                .displayOrder(education.getDisplayOrder())
                .build();
    }

    public static ProjectResponse toProjectResponse(Project project){
        if (project == null) return null;

        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .technologies(project.getTechnologies())
                .projectUrl(project.getProjectUrl())
                .sourceUrl(project.getSourceUrl())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .isOnGoing(project.getIsOnGoing())
                .displayOrder(project.getDisplayOrder())
                .build();
    }

    public static LanguageResponse toLanguageResponse(Language language){
        if (language==null) return null;
        return LanguageResponse.builder()
                .id(language.getId())
                .languageName(language.getLanguageName())
                .proficiency(language.getProficiency())
                .displayOrder(language.getDisplayOrder())
                .build();
    }
}
