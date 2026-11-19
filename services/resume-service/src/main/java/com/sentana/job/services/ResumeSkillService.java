package com.sentana.job.services;

import com.sentana.job.dto.response.ResumeSkillResponse;
import com.sentana.job.models.requests.ResumeSkillRequest;

import java.util.List;

public interface ResumeSkillService {

    ResumeSkillResponse createResumSkill(Long resumeId, Long candidateId, ResumeSkillRequest request) throws Exception;

    List<ResumeSkillResponse> getSkills(Long resumId);

    ResumeSkillResponse updateResumeSkill(Long skillId, Long resumeId, Long candidateId, ResumeSkillRequest request) throws Exception;

    void deleteResumeSkill(Long skillId, Long resumeId, Long candidateId) throws Exception;
}
