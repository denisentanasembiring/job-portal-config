package com.sentana.job.services.impl;

import com.sentana.job.dto.response.JobSkillResponse;
import com.sentana.job.mappers.JobSkillMapper;
import com.sentana.job.models.entities.JobSkill;
import com.sentana.job.models.requests.JobSkillRequest;
import com.sentana.job.respositories.JobSkillRepository;
import com.sentana.job.services.JobSkillService;
import com.sentana.job.utils.GenerateUniqueSlug;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobSkillServiceImplement implements JobSkillService {

    private  static JobSkillRepository jobSkillRepository;

    @Override
    public JobSkillResponse createJobSkill(JobSkillRequest request) throws Exception {
        if(jobSkillRepository.existsByName(request.getName())){
            throw new Exception("skill name already exist");
        }
        String slug = generateUniqueSlug(request.getName());
        JobSkill skill = JobSkill.builder()
                .name(request.getName())
                .slug(slug)
                .skillCategory(request.getSkillCategory())
                .active(true)
                .build();
        JobSkill save = jobSkillRepository.save(skill);
        return JobSkillMapper.toJobSkillResponse(save);
    }

    @Override
    public List<JobSkillResponse> getAllSkills() {
        return jobSkillRepository.findByActiveTrue()
                .stream()
                .map(JobSkillMapper::toJobSkillResponse)
                .collect(Collectors.toList());
    }

    @Override
    public JobSkillResponse getAllSkillById(Long id) throws Exception {
        JobSkill skill = jobSkillRepository.findById(id).orElseThrow(()->new Exception("job skill not found"));
        return JobSkillMapper.toJobSkillResponse(skill);
    }

    @Override
    public JobSkillResponse updateSkill(Long id, JobSkillRequest request) throws Exception {
        JobSkill skill = jobSkillRepository.findById(id).orElseThrow(()-> new Exception("job skill not found"));
        if (!skill.getName().equals(request.getName()) && jobSkillRepository.existsByName(skill.getName())){
            throw new Exception("skill name already exist");
        }
        skill.setName(request.getName());
        skill.setSkillCategory(request.getSkillCategory());
        JobSkill updated = jobSkillRepository.save(skill);
        return JobSkillMapper.toJobSkillResponse(updated);
    }

    @Override
    public void deleteJobSkill(Long id) throws Exception {
        JobSkill skill = jobSkillRepository.findById(id).orElseThrow(()->new Exception("job skill not found"));
        skill.setActive(false);
        jobSkillRepository.save(skill);
    }

    @Override
    public Set<JobSkill> getSkillsById(Set<Long> id) {
        return new HashSet<>(jobSkillRepository.findAllById(id));
    }


    private String generateUniqueSlug(String name){
        String base = GenerateUniqueSlug.execute(name);
        if(!jobSkillRepository.existsByName(base)){
            return base;
        }
        int counter = 1;
        while (jobSkillRepository.existsBySlug(base + "-" + counter)){
            counter++;
        }
        return base + "-" + counter;
    }
}
