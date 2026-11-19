package com.sentana.job.services;

import com.sentana.job.dto.response.LanguageResponse;
import com.sentana.job.models.requests.LanguageRequest;

import java.util.List;

public interface LanguageService {
    LanguageResponse createLanguage(Long resumeId, Long candidateId, LanguageRequest request) throws Exception;
    List<LanguageResponse> getLanguages(Long resumeId);
    LanguageResponse updateLanguage(Long languageId, Long resumeId, Long candidateId, LanguageRequest request) throws Exception;
    void deleteLanguage(Long languageIg, Long resumeId, Long candidateId) throws Exception;

}
