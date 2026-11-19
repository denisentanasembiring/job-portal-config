package com.sentana.job.services.impl;

import com.sentana.job.dto.response.JobTagResponse;
import com.sentana.job.mappers.JobTagMapper;
import com.sentana.job.models.entities.JobTag;
import com.sentana.job.models.requests.JobTagRequest;
import com.sentana.job.respositories.JobTagRepository;
import com.sentana.job.services.JobTagService;
import com.sentana.job.utils.GenerateUniqueSlug;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobTagServiceImplement implements JobTagService {

    private final JobTagRepository jobTagRepository;
    @Override
    public JobTagResponse createTag(JobTagRequest request) throws Exception {
        if(jobTagRepository.existsByName(request.getName())){
            throw new Exception("tag name already exist");
        }
        String slug = generateUniqueSlug(request.getName());
        JobTag tag = JobTag.builder()
                .name(request.getName())
                .slug(slug)
                .build();
        JobTag save = jobTagRepository.save(tag);
        return JobTagMapper.toJobTagResponse(save);
    }

    @Override
    public List<JobTagResponse> getAllJobTags() {
        return jobTagRepository.findAll().stream()
                .map(JobTagMapper::toJobTagResponse)
                .collect(Collectors.toList());
    }

    @Override
    public JobTagResponse getTagById(Long id) throws Exception {
        JobTag tag = getTagEntityById(id);
        return JobTagMapper.toJobTagResponse(tag);
    }

    @Override
    public JobTagResponse updateTag(Long id, JobTagRequest request) throws Exception {
        JobTag tag = getTagEntityById(id);
        if(!tag.getName().equals(request.getName())&& jobTagRepository.existsByName(request.getName())){
            throw new Exception("tag name already exist");
        }
        tag.setName(request.getName());
        return JobTagMapper.toJobTagResponse(jobTagRepository.save(tag));
    }

    @Override
    public void deleteTag(Long id) throws Exception {
        JobTag tag = getTagEntityById(id);
        jobTagRepository.delete(tag);
    }

    @Override
    public JobTag getTagEntityById(Long id) throws Exception {
        return jobTagRepository.findById(id).orElseThrow(()->new Exception("job tag not found"));
    }

    @Override
    public Set<JobTag> getTagsById(Set<Long> id) throws Exception {
        return new HashSet<>(jobTagRepository.findAllById(id));
    }

    private String generateUniqueSlug(String name){
        String base = GenerateUniqueSlug.execute(name);
        if(!jobTagRepository.existsBySlug(base)){
            return base;
        }
        int counter = 1;
        while (jobTagRepository.existsBySlug(base + "-" + counter)){
            counter++;
        }
        return base + "-" + counter;
    }
}
