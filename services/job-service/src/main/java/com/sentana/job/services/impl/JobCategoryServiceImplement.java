package com.sentana.job.services.impl;

import com.sentana.job.dto.response.JobCategoryResponse;
import com.sentana.job.mappers.JobCategoryMapper;
import com.sentana.job.models.entities.JobCategory;
import com.sentana.job.models.requests.JobCategoryRequest;
import com.sentana.job.respositories.JobCategoryRepository;
import com.sentana.job.services.JobCategoryService;
import com.sentana.job.utils.GenerateUniqueSlug;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobCategoryServiceImplement implements JobCategoryService {

    private final JobCategoryRepository jobCategoryRepository;

    @Override
    public JobCategoryResponse createCategory(JobCategoryRequest request) throws Exception {
        if (jobCategoryRepository.existsByName(request.getName())){
            throw new Exception("category name already exist");
        }
        JobCategory parent=null;
        if (request.getParentId()!=null){
            parent=getCategoryEntityById(request.getParentId());
        }
        String slug = generateUniqueSlug(request.getName());
        JobCategory category = JobCategory.builder()
                .name(request.getName())
                .slug(slug)
                .description(request.getDescription())
                .iconUrl(request.getIconUrl())
                .parent(parent)
                .build();
        JobCategory saved = jobCategoryRepository.save(category);
        return JobCategoryMapper.toJobCategoryResponse(saved, true);
    }

    @Override
    public List<JobCategoryResponse> getAllCategories() {
        return jobCategoryRepository.findByActiveTrue()
                .stream()
                .map(c->JobCategoryMapper.toJobCategoryResponse(c, false))
                .collect(Collectors.toList());
    }

    @Override
    public JobCategoryResponse getCategoryById(Long id) throws Exception {
        JobCategory category= getCategoryEntityById(id);
        return JobCategoryMapper.toJobCategoryResponse(category, true);
    }

    @Override
    public JobCategoryResponse updateCategory(Long id, JobCategoryRequest request) throws Exception {
        JobCategory category = getCategoryEntityById(id);
        if (!category.getName().equals(request.getName()) && jobCategoryRepository.existsByName(request.getName())){
            throw new Exception("category name already exist, choose different name");
        }
        JobCategory parent = null;
        if (request.getParentId()!=null){
            if(request.getParentId().equals(id)){
                throw new Exception("a category cannot be its own parent");
            }
            parent = getCategoryEntityById(request.getParentId());
        }
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setIconUrl(request.getIconUrl());
        category.setParent(parent);
        JobCategory updated = jobCategoryRepository.save(category);
        return JobCategoryMapper.toJobCategoryResponse(updated, true);
    }

    @Override
    public void deleteCategory(Long id) throws Exception {
        JobCategory category = getCategoryEntityById(id);
        category.setActive(false);
        jobCategoryRepository.save(category);
    }

    @Override
    public JobCategory getCategoryEntityById(Long id) throws Exception {
        return jobCategoryRepository.findById(id).orElseThrow(
                ()->new Exception("category not found")
        );
    }

    private String generateUniqueSlug(String name) {
        String base = GenerateUniqueSlug.execute(name);
        if (!jobCategoryRepository.existsBySlug(base)){
            return base;
        }

        int counter=1;
        while(jobCategoryRepository.existsBySlug(base+"-"+counter)){
            counter++;
        }
        return base +"-"+counter;
    }
}
