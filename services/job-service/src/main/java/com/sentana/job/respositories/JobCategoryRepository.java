package com.sentana.job.respositories;

import com.sentana.job.models.entities.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobCategoryRepository extends JpaRepository<JobCategory, Long> {
    boolean existsByName(String name);
    boolean existsBySlug(String slug);

    List<JobCategory> findByActiveTrue();
}
