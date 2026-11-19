package com.sentana.job.repositories;

import com.sentana.job.domain.AiSortListStatus;
import com.sentana.job.domain.ApplicationStatus;
import com.sentana.job.models.entities.Application;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ApplicationSpecification {

    public static Specification<Application> forCompanyWithFilter(
            Long companyId,
            Long jobId,
            ApplicationStatus status,
            boolean isStarred,
            AiSortListStatus aiSortListStatus,
            Integer minAiScore
    ){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("companyId"), companyId));
            if (jobId != null) predicates.add(criteriaBuilder.equal(root.get("jobId"), jobId));
            if (status != null) predicates.add(criteriaBuilder.equal(root.get("status"), status));
            if (isStarred) predicates.add(criteriaBuilder.equal(root.get("isStarred"), isStarred));
            if (aiSortListStatus != null) predicates.add(criteriaBuilder.equal(root.get("aiSortListStatus"), aiSortListStatus));
            if (minAiScore != null) predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("aiScore"), minAiScore));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
