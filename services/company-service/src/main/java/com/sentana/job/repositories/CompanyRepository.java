package com.sentana.job.repositories;

import com.sentana.job.domain.CompanyStatus;
import com.sentana.job.domain.CompanyType;
import com.sentana.job.domain.IndustryType;
import com.sentana.job.models.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByOwnerId(Long ownerId);
    boolean existsByOwnerId(Long id);
    boolean existsByCompanyName(String name);
    boolean existsBySlug(String slug);
    boolean existsByRegistrationNumber(String numberRegistration);

    @Query(
            "select c from Company c where " +
            "(:companyType IS NULL OR c.companyType=:companyType) AND" +
            "(:industryType IS NULL OR c.industryType=:industryType) AND " +
            "(:status IS NULL OR c.companyStatus=:status)"
    )
    List<Company> findByFilter(
            @Param("companyType") CompanyType companyType,
            @Param("industryType") IndustryType industryType,
            @Param("status")CompanyStatus companyStatus
            );
}
