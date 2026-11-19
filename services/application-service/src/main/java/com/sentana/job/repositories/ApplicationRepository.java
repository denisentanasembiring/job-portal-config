package com.sentana.job.repositories;

import com.sentana.job.models.entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long>, JpaSpecificationExecutor<Application> {

    List<Application> findByCandidateId(Long candidateId);
    List<Application> findByJobId(Long candidateId);
    boolean existsByCandidateIdAndJobId(Long candidateId, Long jobId);
}
