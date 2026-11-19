package com.sentana.job.repositories;

import com.sentana.job.models.entities.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    List<Language> findByResume_IdOrderByDisplayOrder(Long resumeId);
}
