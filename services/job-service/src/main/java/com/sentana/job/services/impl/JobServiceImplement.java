package com.sentana.job.services.impl;

import com.sentana.job.domain.JobStatus;
import com.sentana.job.dto.response.CompanyResponse;
import com.sentana.job.mappers.JobMapper;
import com.sentana.job.models.embed.JobLocation;
import com.sentana.job.models.embed.SalaryRange;
import com.sentana.job.models.entities.Job;
import com.sentana.job.models.entities.JobCategory;
import com.sentana.job.models.entities.JobSkill;
import com.sentana.job.models.entities.JobTag;
import com.sentana.job.models.requests.JobRequest;
import com.sentana.job.models.requests.JobSearchRequest;
import com.sentana.job.dto.response.JobResponse;
import com.sentana.job.respositories.JobRepository;
import com.sentana.job.respositories.JobSpecification;
import com.sentana.job.services.JobCategoryService;
import com.sentana.job.services.JobService;
import com.sentana.job.services.JobSkillService;
import com.sentana.job.services.JobTagService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class JobServiceImplement implements JobService {

    private final JobRepository jobRepository;
    private final JobCategoryService categoryService;
    private final JobSkillService jobSkillService;
    private final JobTagService jobTagService;

    @Override
    public JobResponse createJob(Long employerId, JobRequest request) throws Exception {
        Long companyId=1L;
        JobCategory category = categoryService.getCategoryEntityById(request.getCategoryId());
        Set<JobSkill> skills = request.getSkillIds()!=null?jobSkillService.getSkillsById(request.getSkillIds()): Collections.emptySet();
        Set<JobTag> tags = request.getTagIds()!=null?jobTagService.getTagsById(request.getSkillIds()): Collections.emptySet();


        Job job = Job.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .requirements(request.getRequirements())
                .responsibilities(request.getResponsibilities())
                .benefits(request.getBenefits())
                .companyId(companyId)
                .employerId(request.getCategoryId())
                .category(category)
                .skills(skills)
                .tags(tags)
                .location(locationBuild(request))
                .salaryRange(salaryRangeBuild(request))
                .jobType(request.getJobType())
                .workMode(request.getWorkMode())
                .experienceLevel(request.getExperienceLevel())
                .openings(request.getOpenings() !=null ? request.getOpenings() : 1)
                .applicationDeadline(request.getApplicationDeadline())
                .expiredAt(request.getExpiredAt())
                .build();

        Job save = jobRepository.save(job);
        return convertToResponse(save);
    }

    @Override
    public JobResponse getJobById(Long id) throws Exception {
        Job job = jobRepository.findById(id).orElseThrow(()-> new Exception("job not found"));
        return convertToResponse(job);
    }

    @Override
    public List<JobResponse> getJobs(JobSearchRequest request) {
        List<Job> jobs = jobRepository.findAll(JobSpecification.build(request));
        return jobs.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public List<JobResponse> getJobsByCompany(Long companyId) {
        List<Job> jobs = jobRepository.findByCompanyId(companyId);
        return jobs.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public JobResponse updateJob(Long jobId, Long employerId, JobRequest request) throws Exception {
        Job job = jobRepository.findById(jobId).orElseThrow(()-> new Exception("job not found"));
        JobCategory category = categoryService.getCategoryEntityById(request.getCategoryId());
        Set<JobSkill> skills = request.getSkillIds()!=null?jobSkillService.getSkillsById(request.getSkillIds()): Collections.emptySet();
        Set<JobTag> tags = request.getTagIds()!=null?jobTagService.getTagsById(request.getSkillIds()): Collections.emptySet();
        assertEmployer(job, employerId);
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setRequirements(request.getRequirements());
        job.setResponsibilities(request.getResponsibilities());
        job.setBenefits(request.getBenefits());
        job.setCategory(category);
        job.setSkills(skills);
        job.setTags(tags);
        job.setLocation(locationBuild(request));
        job.setSalaryRange(salaryRangeBuild(request));
        job.setJobType(request.getJobType());
        job.setWorkMode(request.getWorkMode());
        job.setExperienceLevel(request.getExperienceLevel());
        job.setOpenings(request.getOpenings()!=null ? request.getOpenings() : job.getOpenings());
        job.setApplicationDeadline(request.getApplicationDeadline());
        job.setExpiredAt(request.getExpiredAt());
        return convertToResponse(jobRepository.save(job));
    }

    @Override
    public JobResponse publishJob(Long jobId, Long employerId) throws Exception {
        Job job = jobRepository.findById(jobId).orElseThrow(()->new Exception("job not found"));

        assertEmployer(job, employerId);
        if (job.getJobStatus()==JobStatus.CLOSED || job.getJobStatus()==JobStatus.EXPIRED){
            throw new Exception("job is closed/expired");
        }
        job.setJobStatus(JobStatus.OPEN);
        job.setPublishedAt(LocalDateTime.now());
        job.setActive(true);
        return convertToResponse(jobRepository.save(job));
    }

    @Override
    public JobResponse closeJob(Long jobId, Long employerId) throws Exception {
        Job job = jobRepository.findById(jobId).orElseThrow(()->new Exception("job not found"));

        assertEmployer(job, employerId);
        job.setJobStatus(JobStatus.CLOSED);
        job.setPublishedAt(LocalDateTime.now());
        job.setActive(false);
        return convertToResponse(jobRepository.save(job));
    }

    @Override
    public void deleteJob(Long jobId, Long employerId) throws Exception {
        Job job = jobRepository.findById(jobId).orElseThrow(()->new Exception("job not found"));
        assertEmployer(job, employerId);
        jobRepository.delete(job);
    }

    @Override
    public List<JobResponse> getAllJobsAmin() {
        return jobRepository.findAll().stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    private JobLocation locationBuild(JobRequest request) {
        return JobLocation.builder()
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .zipCode(request.getZipCode())
                .build();
    }

    private SalaryRange salaryRangeBuild(JobRequest request) {
        return SalaryRange.builder()
                .minSalary(request.getMinSalary())
                .maxSalary(request.getMaxSalary())
                .build();
    }

    private JobResponse convertToResponse(Job save) {
        // todo: fetch company response
        CompanyResponse companyResponse = CompanyResponse.builder()
                .id(save.getCompanyId())
                .build();
        return JobMapper.toResponse(save, companyResponse);
    }

    private void assertEmployer(Job job, Long employerId) throws Exception {
        if (!job.getEmployerId().equals(employerId)){
            throw new Exception("you are not the employer who posted this job");
        }
    }

}
