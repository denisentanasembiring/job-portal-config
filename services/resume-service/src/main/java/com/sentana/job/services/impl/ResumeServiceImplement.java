package com.sentana.job.services.impl;

import com.sentana.job.dto.response.PersonalInformationResponse;
import com.sentana.job.dto.response.ResumeResponse;
import com.sentana.job.mappers.ResumeMapper;
import com.sentana.job.models.entities.PersonalInformation;
import com.sentana.job.models.entities.Resume;
import com.sentana.job.models.requests.ResumeRequest;
import com.sentana.job.repositories.ResumeRepository;
import com.sentana.job.services.ResumeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ResumeServiceImplement implements ResumeService {

    private final ResumeRepository resumeRepository;

    @Override
    public ResumeResponse createResume(Long candidateId, ResumeRequest request) {
        if (Boolean.TRUE.equals(request.getIsDefault())){
            resumeRepository.findByCandidateIdAndIsDefaultTrue(candidateId)
                    .ifPresent(exist->{exist.setIsDefault(false);
                        resumeRepository.save(exist);
                    });
        }
        Resume resume = Resume.builder()
                .candidateId(candidateId)
                .title(request.getTitle())
                .template(request.getResumeTemplate())
                .visibility(request.getResumeVisibility())
                .isDefault(Boolean.TRUE.equals(request.getIsDefault()))
                .isActive(true)
                .build();
        Resume save = resumeRepository.save(resume);
        return buildFullResponse(save);
    }

    @Override
    public List<ResumeResponse> getResumes(Long candidateId) {
        return resumeRepository.findByCandidateIdAndIsActiveTrue(candidateId)
                .stream().map(this::buildFullResponse).toList();
    }

    @Override
    public ResumeResponse getResumeById(Long id, Long candidateId) throws Exception {
        Resume resume = getResumeEntity(id);
        assertOwner(resume, candidateId);
        return buildFullResponse(resume);
    }


    @Override
    public ResumeResponse updatePersonalInformation(Long id, Long candidateId, PersonalInformationResponse request) throws Exception {
        Resume resume = getResumeEntity(id);
        assertOwner(resume, candidateId);
        PersonalInformation information = resume.getPersonalInformation();
        if (information==null) information= new PersonalInformation();
        if (request.getFirstname()!=null)information.setFirstname(request.getFirstname());
        if (request.getLastname()!=null) information.setLastname(request.getLastname());
        if (request.getHeadline()!=null) information.setHeadline(request.getHeadline());
        if (request.getEmail()!=null) information.setEmail(request.getEmail());
        if (request.getPhoneNumber()!=null) information.setPhoneNumber(request.getPhoneNumber());
        if (request.getCity()!=null) information.setCity(request.getCity());
        if (request.getCountry()!=null) information.setCountry(request.getCountry());
        if (request.getLinkedinUrl()!=null) information.setLinkedinUrl(request.getLinkedinUrl());
        if (request.getGithubUrl()!=null) information.setGithubUrl(request.getGithubUrl());
        if (request.getPortfolioUrl()!=null) information.setPortfolioUrl(request.getPortfolioUrl());
        if (request.getWebsiteUrl()!=null) information.setWebsiteUrl(request.getWebsiteUrl());

        resume.setPersonalInformation(information);
        Resume update = resumeRepository.save(resume);
        return buildFullResponse(update);
    }

    @Override
    public ResumeResponse updateSummary(Long id, Long candidateId, String summary) throws Exception {
        Resume resume = getResumeEntity(id);
        assertOwner(resume, candidateId);
        resume.setSummary(summary);
        Resume update=resumeRepository.save(resume);
        return buildFullResponse(update);
    }

    @Override
    public ResumeResponse setDefaultResume(Long id, Long candidateId) throws Exception {
        Resume resume = getResumeEntity(id);
        assertOwner(resume, candidateId);
        resumeRepository.findByCandidateIdAndIsDefaultTrue(candidateId)
                .ifPresent(exist->{exist.setIsDefault(false);
                    resumeRepository.save(exist);
                });
        resume.setIsDefault(true);
        Resume update = resumeRepository.save(resume);
        return buildFullResponse(update);
    }

    @Override
    public void deleteResume(Long id, Long candidateId) throws Exception {
        Resume resume = getResumeEntity(id);
        assertOwner(resume, candidateId);
        resume.setIsActive(false);
        resume.setIsDefault(false);
    }

    @Override
    public Resume getResumeEntity(Long id) throws Exception {
        return resumeRepository.findById(id).orElseThrow(()->new Exception("resume with id " + id + "not found"));
    }

    private ResumeResponse buildFullResponse(Resume resume){
        return ResumeMapper.toResponse(resume);
    }

    private void assertOwner(Resume resume, Long candidateId) throws Exception {
        if (!resume.getCandidateId().equals(candidateId)){
            throw new Exception("resume with id " +resume.getId()+ "not found");
        }
    }
}
