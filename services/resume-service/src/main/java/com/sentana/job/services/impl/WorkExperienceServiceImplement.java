package com.sentana.job.services.impl;

import com.sentana.job.dto.response.WorkExperienceResponse;
import com.sentana.job.mappers.WorkExperienceMapper;
import com.sentana.job.models.entities.Resume;
import com.sentana.job.models.entities.WorkExperience;
import com.sentana.job.models.requests.WorkExperienceRequest;
import com.sentana.job.repositories.WorkExperienceRepository;
import com.sentana.job.services.ResumeService;
import com.sentana.job.services.WorkExperienceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkExperienceServiceImplement implements WorkExperienceService {

    private final WorkExperienceRepository experienceRepository;
    private final ResumeService resumeService;

    @Override
    public WorkExperienceResponse createExperience(Long resumeId, Long candidateId, WorkExperienceRequest request) throws Exception {
        Resume resume = resumeService.getResumeEntity(resumeId);

        assertOwner(resume, candidateId);

        WorkExperience experience = WorkExperience.builder()
                .resume(resume)
                .companyName(request.getCompanyName())
                .companyLogUrl(request.getCompanyLogoUrl())
                .jobTitle(request.getJobTitle())
                .employmentType(request.getEmploymentType())
                .location(request.getLocation())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .isCurrentJob(Boolean.TRUE.equals(request.getIsCurrentJob()))
                .description(request.getDescription())
                .technologies(request.getTechnologies()!=null ? request.getTechnologies(): List.of())
                .displayOrder(request.getDisplayOrder()!=null ? request.getDisplayOrder(): 0)
                .build();

        WorkExperience saved = experienceRepository.save(experience);
        return WorkExperienceMapper.toExperienceResponse(saved);
    }


    @Override
    public List<WorkExperienceResponse> getExperiences(Long resumeId) {
        return experienceRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId)
                .stream().map(WorkExperienceMapper::toExperienceResponse).toList();
    }

    @Override
    public WorkExperienceResponse updateExperience(Long resumeId, Long candidateId, Long experienceId, WorkExperienceRequest request) throws Exception {
        WorkExperience experience=getExperienceEntity(experienceId);
        assertOwner(experience.getResume(), candidateId);
        experience.setCompanyName(request.getCompanyName());
        experience.setCompanyLogUrl(request.getCompanyLogoUrl());
        experience.setJobTitle(request.getJobTitle());
        experience.setEmploymentType(request.getEmploymentType());
        experience.setLocation(request.getLocation());
        experience.setStartDate(request.getStartDate());
        experience.setEndDate(request.getEndDate());
        experience.setIsCurrentJob(Boolean.TRUE.equals(request.getIsCurrentJob()));
        experience.setDescription(request.getDescription());
        if (request.getTechnologies()!=null) experience.setTechnologies(request.getTechnologies());
        if (request.getDisplayOrder()!=null) experience.setDisplayOrder(request.getDisplayOrder());

        WorkExperience save = experienceRepository.save(experience);
        return WorkExperienceMapper.toExperienceResponse(save);
    }

    @Override
    public void deleteExperience(Long resumeId, Long experienceId, Long candidateId) throws Exception {
        WorkExperience experience=getExperienceEntity(experienceId);
        assertOwner(experience.getResume(), candidateId);
        experienceRepository.delete(experience);
    }

    @Override
    public WorkExperience getExperienceEntity(Long experienceId) throws Exception {
        return experienceRepository.findById(experienceId).orElseThrow(
                ()->new Exception ("experience not found")
        );
    }

    private void assertOwner(Resume resume, Long candidateId) throws Exception {
        if (!resume.getCandidateId().equals(candidateId)){
            throw new Exception("resum not found");
        }
    }


}
