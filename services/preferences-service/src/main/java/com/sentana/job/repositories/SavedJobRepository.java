package com.sentana.job.repositories;

import com.sentana.job.models.entities.SavedJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavedJobRepository extends JpaRepository<SavedJob, Long> {

    List<SavedJob> findByCandidateId(Long candidateId);

    boolean existsByCandidateIdAndJobId(Long candidate, Long jobId);
}
