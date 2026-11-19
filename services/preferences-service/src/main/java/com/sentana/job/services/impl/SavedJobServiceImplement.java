package com.sentana.job.services.impl;

import com.sentana.job.dto.response.SavedJobResponse;
import com.sentana.job.mappers.PreferenceMapper;
import com.sentana.job.models.entities.SavedJob;
import com.sentana.job.models.requests.SavedJobRequest;
import com.sentana.job.repositories.SavedJobRepository;
import com.sentana.job.services.SavedJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavedJobServiceImplement implements SavedJobService {

    private final SavedJobRepository savedJobRepository;

    @Override
    public SavedJobResponse saveJob(Long candidateId, SavedJobRequest request) throws Exception {
        if (isSaved(candidateId, request.getJobId())){
            throw new Exception("job already saved");
        }
        SavedJob savedJob = SavedJob.builder()
                .candidateId(candidateId)
                .jobId(request.getJobId())
                .build();
        return PreferenceMapper.toSavedJobResponse(savedJobRepository.save(savedJob));
    }

    @Override
    public void unsaveJob(Long candidateId, Long savedJobId) throws Exception {
        SavedJob savedJob = savedJobRepository.findById(savedJobId).orElseThrow(
                ()-> new Exception(""));
        if (!savedJob.getCandidateId().equals(candidateId)){
            throw new Exception("job not saved");
        }
        savedJobRepository.delete(savedJob);
    }

    @Override
    public List<SavedJobResponse> getSavedJob(Long candidateId) {
        return savedJobRepository.findByCandidateId(candidateId).stream().map(PreferenceMapper::toSavedJobResponse).toList();
    }

    @Override
    public boolean isSaved(Long candidateId, Long jobId) {
        return savedJobRepository.existsByCandidateIdAndJobId(candidateId, jobId);
    }
}
