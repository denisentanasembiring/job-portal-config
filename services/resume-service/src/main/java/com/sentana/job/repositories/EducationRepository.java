package com.sentana.job.repositories;

import com.sentana.job.models.entities.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long> {
    List<Education> findByResume_IdOrderByDisplayOrderAsc(Long resumeId);
}
