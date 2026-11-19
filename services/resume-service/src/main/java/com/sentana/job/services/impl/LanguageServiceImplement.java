package com.sentana.job.services.impl;

import com.sentana.job.dto.response.LanguageResponse;
import com.sentana.job.mappers.ResumeMapper;
import com.sentana.job.models.entities.Language;
import com.sentana.job.models.entities.Resume;
import com.sentana.job.models.requests.LanguageRequest;
import com.sentana.job.repositories.LanguageRepository;
import com.sentana.job.services.LanguageService;
import com.sentana.job.services.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageServiceImplement implements LanguageService {

    private final LanguageRepository languageRepository;
    private final ResumeService resumeService;

    @Override
    public LanguageResponse createLanguage(Long resumeId, Long candidateId, LanguageRequest request) throws Exception {
        Resume resume = resumeService.getResumeEntity(resumeId);
        assertOwner(resume, candidateId);

        Language language = Language.builder()
                .resume(resume)
                .languageName(request.getLanguageName())
                .proficiency(request.getProficiency())
                .displayOrder(request.getDisplayOrder()!=null ? request.getDisplayOrder():0)
                .build();
        return ResumeMapper.toLanguageResponse(languageRepository.save(language));
    }

    @Override
    public List<LanguageResponse> getLanguages(Long resumeId) {
        return languageRepository.findByResume_IdOrderByDisplayOrder(resumeId)
                .stream()
                .map(ResumeMapper::toLanguageResponse).toList();
    }

    @Override
    public LanguageResponse updateLanguage(Long languageId, Long resumeId, Long candidateId, LanguageRequest request) throws Exception {
        Language language = languageRepository.findById(languageId).orElseThrow(
                ()->new Exception("language not found")
        );
        assertOwner(language.getResume(), candidateId);
        language.setLanguageName(request.getLanguageName());
        language.setProficiency(request.getProficiency());
        if (request.getDisplayOrder()!=null) language.setDisplayOrder(request.getDisplayOrder());
        return ResumeMapper.toLanguageResponse(languageRepository.save(language));
    }

    @Override
    public void deleteLanguage(Long languageId, Long resumeId, Long candidateId) throws Exception {
        Language language = languageRepository.findById(languageId).orElseThrow(
                ()->new Exception("language not found")
        );
        assertOwner(language.getResume(), candidateId);
        languageRepository.delete(language);
    }

    private void assertOwner(Resume resume, Long candidateId) throws Exception {
        if (!resume.getCandidateId().equals(candidateId)){
            throw new Exception("resume with id " +resume.getId()+ "not found");
        }
    }
}
