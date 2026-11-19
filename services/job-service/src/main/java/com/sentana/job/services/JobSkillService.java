package com.sentana.job.services;

import com.sentana.job.dto.response.JobSkillResponse;
import com.sentana.job.models.entities.JobSkill;
import com.sentana.job.models.requests.JobSkillRequest;

import java.util.List;
import java.util.Set;

public interface JobSkillService {
    JobSkillResponse createJobSkill(JobSkillRequest request) throws Exception;
    List<JobSkillResponse> getAllSkills();
    JobSkillResponse getAllSkillById(Long id) throws Exception;
    JobSkillResponse updateSkill(Long id, JobSkillRequest request) throws Exception;
    void deleteJobSkill(Long id) throws Exception;
    Set<JobSkill> getSkillsById(Set<Long> id);
}
