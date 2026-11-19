package com.sentana.job.services;

import com.sentana.job.domain.CompanyStatus;
import com.sentana.job.domain.CompanyType;
import com.sentana.job.domain.IndustryType;
import com.sentana.job.dto.response.CompanyResponse;
import com.sentana.job.models.entities.Company;
import com.sentana.job.models.requests.CompanyRequest;

import java.util.List;

public interface CompanyService {

    CompanyResponse create(Long ownerId, CompanyRequest request) throws Exception;
    CompanyResponse getById(Long id) throws Exception;
    CompanyResponse getMyCompany(Long ownerId) throws Exception;
    List<CompanyResponse> gets(CompanyType companyType, IndustryType industryType, CompanyStatus companyStatus);
    CompanyResponse update(Long companyId, Long ownerId, CompanyRequest request) throws Exception;
    CompanyResponse verify(Long companyId) throws Exception;
    void delete(Long companyId, Long ownerId) throws Exception;
    CompanyResponse deactivateCompany(Long companyId) throws Exception;
    Company getCompanyEntityById(Long companyId) throws Exception;
}
