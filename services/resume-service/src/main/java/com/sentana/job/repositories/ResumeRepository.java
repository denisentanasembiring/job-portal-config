package com.sentana.job.repositories;

import com.sentana.job.models.entities.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    List<Resume> findByCandidateIdAndIsActiveTrue(Long id);

    Optional<Resume> findByCandidateIdAndIsDefaultTrue(Long resumeId);

}
