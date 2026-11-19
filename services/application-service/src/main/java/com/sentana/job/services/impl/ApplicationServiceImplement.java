package com.sentana.job.services.impl;

import com.sentana.job.domain.ApplicationStatus;
import com.sentana.job.dto.response.ApplicationResponse;
import com.sentana.job.dto.response.CompanyResponse;
import com.sentana.job.dto.response.JobResponse;
import com.sentana.job.dto.response.UserResponse;
import com.sentana.job.mappers.ApplicationMapper;
import com.sentana.job.models.entities.Application;
import com.sentana.job.models.entities.ApplicationNote;
import com.sentana.job.models.requests.ApplicationRequest;
import com.sentana.job.models.requests.CompanyApplicationFilterRequest;
import com.sentana.job.models.requests.WithDrawApplicationRequest;
import com.sentana.job.repositories.ApplicationNoteRepository;
import com.sentana.job.repositories.ApplicationRepository;
import com.sentana.job.repositories.ApplicationSpecification;
import com.sentana.job.services.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImplement implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationNoteRepository applicationNoteRepository;

    @Override
    public ApplicationResponse createApplication(Long candidateId, ApplicationRequest request) throws Exception {
        if (applicationRepository.existsByCandidateIdAndJobId(candidateId, request.getJobId())){
            throw new Exception("you have already applied");
        }
        Long companyId =1L;
        Long employeeId=1L;
        // todo : fetch job

        // todo: fetch resume
        Application application = ApplicationMapper.toEntity(request, candidateId, companyId, employeeId);
        return null;
    }

    @Override
    public ApplicationResponse getApplicationById(Long applicationId) throws Exception {
        Application application = getApplicationEntity(applicationId);
        return null;
    }

    @Override
    public List<ApplicationResponse> getMyApplications(Long candidateId) {
        return applicationRepository.findByCandidateId(candidateId)
                .stream().map(this::buildFullResponse).toList();
    }

    @Override
    public List<ApplicationResponse> getApplicationForJob(Long jobId) {
        return applicationRepository.findByJobId(jobId)
                .stream().map(this::buildFullResponse).toList();
    }

    @Override
    public List<ApplicationResponse> getApplicationForCompany(Long userId,

                                                              CompanyApplicationFilterRequest filter) {
        Long companyId = 1L;
        Sort sort = buildSort(filter.getSortBy());
        return applicationRepository.findAll(ApplicationSpecification.forCompanyWithFilter(
                companyId,
                filter.getJobId(),
                filter.getStatus(),
                filter.getIsStarred(),
                filter.getAiSortListStatus(),
                filter.getMinAiScore()
                ), sort)
                .stream().map(this::buildFullResponse).toList();
    }

    @Override
    public ApplicationResponse updateStatus(Long applicationId, Long employerId, ApplicationStatus status) throws Exception {
        Application application = getApplicationEntity(applicationId);
        assertEmployer(application, employerId);
        if(application.getApplicationStatus()==ApplicationStatus.WITHDRAW){
            throw new Exception("candidate have already withdrawn");
        }
        application.setApplicationStatus(status);
        return buildFullResponse(applicationRepository.save(application));
    }

    @Override
    public ApplicationResponse withdraw(Long applicationId, Long candidateId, WithDrawApplicationRequest request) throws Exception {
        Application application = getApplicationEntity(applicationId);
        assetCandidate(application, candidateId);
        application.setApplicationStatus(ApplicationStatus.WITHDRAW);
        application.setWithDrawnReason(request.getReason());
        return buildFullResponse(applicationRepository.save(application));
    }

    @Override
    public ApplicationResponse toggleStar(Long applicationId, Long employerId) throws Exception {
        Application application = getApplicationEntity(applicationId);
        assertEmployer(application, employerId);
        application.setIsStarred(!application.getIsStarred());
        return buildFullResponse(applicationRepository.save(application));
    }

    @Override
    public void deleteApplication(Long applicationId, Long candidateId) throws Exception {
        Application application = getApplicationEntity(applicationId);
        assetCandidate(application, candidateId);
        applicationRepository.delete(application);
    }

    @Override
    public Application getApplicationEntity(Long id) throws Exception {
        return applicationRepository.findById(id).orElseThrow(
                ()->new Exception("application not found")
        );
    }

    private ApplicationResponse buildFullResponse(Application application){
        JobResponse job = JobResponse.builder().id(application.getId()).build();
        CompanyResponse company = CompanyResponse.builder().id(application.getCompanyId()).build();
        UserResponse candidate = UserResponse.builder().id(application.getCandidateId()).build();
        List<ApplicationNote> notes = applicationNoteRepository.findByApplicationId(application.getId());
        return ApplicationMapper.toApplicationResponse(
                application,
                notes,
                job,
                company,
                candidate
        );
    }

    private Sort buildSort(String sortBy){
        if ("AI_SCORE_DESC".equals(sortBy)){
            return Sort.by(Sort.Order.desc("aiScore").with(Sort.NullHandling.NULLS_LAST));
        } else if("AI_SCORE_ASC".equals(sortBy)) {
            return Sort.by(Sort.Order.asc("aiScore").with(Sort.NullHandling.NULLS_LAST));
        }
        return Sort.by(Sort.Direction.DESC,"appliedAt");
    }

    private void assertEmployer(Application application, Long employerId) throws Exception {
        if (!application.getEmployerId().equals(employerId)){
            throw new Exception("you're not the employer for this application");
        }
    }

    private void assetCandidate(Application application, Long candidateId) throws Exception {
        if (!application.getCandidateId().equals(candidateId)){
            throw new Exception("you are not the owner of this application");
        }
    }
}
