package com.sentana.job.services.impl;

import com.sentana.job.dto.response.EducationResponse;
import com.sentana.job.mappers.ResumeMapper;
import com.sentana.job.models.entities.Education;
import com.sentana.job.models.entities.Resume;
import com.sentana.job.models.requests.EducationRequest;
import com.sentana.job.repositories.EducationRepository;
import com.sentana.job.services.EducationService;
import com.sentana.job.services.ResumeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EducationServiceImplement implements EducationService {

    private final EducationRepository educationRepository;
    private final ResumeService resumeService;

    @Override
    public EducationResponse addEducation(Long resumeId, Long candidateId, EducationRequest request) throws Exception {
        Resume resume = resumeService.getResumeEntity(resumeId);
        assertOwner(resume, candidateId);
        Education education = Education.builder()
                .resume(resume)
                .institutionName(request.getInstitutionName())
                .degree(request.getDegree())
                .fieldOfStudy(request.getFieldOfStudy())
                .grade(request.getGrade())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .isCurrentlyStudy(Boolean.TRUE.equals(request.getIsCurrentlyStudy()))
                .description(request.getDescription())
                .displayOrder(request.getDisplayOrder()!=null ? request.getDisplayOrder():0)
                .build();
        return ResumeMapper.toEducationResponse(educationRepository.save(education));
    }

    @Override
    public List<EducationResponse> getEducations(Long resumeId) {
        return educationRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId)
                .stream().map(ResumeMapper::toEducationResponse).toList();
    }

    @Override
    public EducationResponse updateEducation(Long educationId, Long resumeId, Long candidateId, EducationRequest request) throws Exception {
        Education education = educationRepository.findById(educationId).orElseThrow(
                ()->new Exception("education does not exist")
        );
        assertOwner(education.getResume(), candidateId);

        education.setInstitutionName(request.getInstitutionName());
        education.setDegree(request.getDegree());
        education.setFieldOfStudy(request.getFieldOfStudy());
        education.setGrade(request.getGrade());
        education.setStartDate(education.getStartDate());
        education.setEndDate(education.getEndDate());
        education.setDescription(education.getDescription());
        if (request.getDisplayOrder()!=null) education.setDisplayOrder(request.getDisplayOrder());

        return ResumeMapper.toEducationResponse(educationRepository.save(education));
    }

    @Override
    public void deleteEducation(Long educationId, Long resumeId, Long candidateId) throws Exception {
        Education education = educationRepository.findById(educationId).orElseThrow(
                ()->new Exception("education does not exist")
        );
        assertOwner(education.getResume(), candidateId);
        educationRepository.delete(education);
    }

    private void assertOwner(Resume resume, Long candidateId) throws Exception {
        if (!resume.getCandidateId().equals(candidateId)){
            throw new Exception("resume with id " +resume.getId()+ "not found");
        }
    }
}
