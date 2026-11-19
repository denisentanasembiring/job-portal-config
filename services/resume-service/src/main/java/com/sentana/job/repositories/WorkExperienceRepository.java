package com.sentana.job.repositories;

import com.sentana.job.models.entities.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {
    List<WorkExperience> findByResume_IdOrderByDisplayOrderAsc(Long resumeId);
}
