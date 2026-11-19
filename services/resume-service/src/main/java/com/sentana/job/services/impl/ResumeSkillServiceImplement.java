package com.sentana.job.services.impl;

import com.sentana.job.dto.response.ResumeSkillResponse;
import com.sentana.job.mappers.ResumeMapper;
import com.sentana.job.models.entities.Resume;
import com.sentana.job.models.entities.ResumeSkill;
import com.sentana.job.models.requests.ResumeSkillRequest;
import com.sentana.job.repositories.ResumeSkillRepository;
import com.sentana.job.services.ResumeService;
import com.sentana.job.services.ResumeSkillService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ResumeSkillServiceImplement implements ResumeSkillService {

    private final ResumeSkillRepository resumeSkillRepository;
    private final ResumeService resumeService;

    @Override
    public ResumeSkillResponse createResumSkill(Long resumeId, Long candidateId, ResumeSkillRequest request) throws Exception {
        Resume resume = resumeService.getResumeEntity(resumeId);
        assertOwner(resume, candidateId);

        ResumeSkill resumeSkill = ResumeSkill.builder()
                .resume(resume)
                .skillName(request.getSkillName())
                .proficiencyLevel(request.getProficiencyLevel())
                .yearOfExperience(request.getYearsOfExperience())
                .displayOrder(request.getDisplayOrder()!=null ? request.getDisplayOrder() : 0)
                .build();
        ResumeSkill save = resumeSkillRepository.save(resumeSkill);
        return ResumeMapper.toSkillResponse(save);
    }

    @Override
    public List<ResumeSkillResponse> getSkills(Long resumId) {
        return resumeSkillRepository.findByResume_IdOrderByDisplayOrderAsc(resumId)
                .stream().map(ResumeMapper::toSkillResponse).toList();
    }

    @Override
    public ResumeSkillResponse updateResumeSkill(Long skillId, Long resumeId, Long candidateId, ResumeSkillRequest request) throws Exception {
        ResumeSkill resumeSkill = resumeSkillRepository.findById(skillId).orElseThrow(
                ()->new Exception("skill not found")
        );
        assertOwner(resumeSkill.getResume(), candidateId);
        resumeSkill.setSkillName(resumeSkill.getSkillName());
        resumeSkill.setProficiencyLevel(request.getProficiencyLevel());
        resumeSkill.setYearOfExperience(resumeSkill.getYearOfExperience());
        if (request.getDisplayOrder()!=null) resumeSkill.setDisplayOrder(resumeSkill.getDisplayOrder());
        return ResumeMapper.toSkillResponse(resumeSkillRepository.save(resumeSkill));
    }

    @Override
    public void deleteResumeSkill(Long skillId, Long resumeId, Long candidateId) throws Exception {
        ResumeSkill resumeSkill = resumeSkillRepository.findById(skillId).orElseThrow(
                ()->new Exception("skill not found")
        );
        resumeSkillRepository.delete(resumeSkill);
    }

     private void assertOwner(Resume resume, Long candidateId) throws Exception {
        if (!resume.getCandidateId().equals(candidateId)){
            throw new Exception("resume with id " +resume.getId()+ "not found");
        }
    }
}
