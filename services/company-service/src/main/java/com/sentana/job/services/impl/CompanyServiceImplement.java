package com.sentana.job.services.impl;

import com.sentana.job.domain.CompanyStatus;
import com.sentana.job.domain.CompanyType;
import com.sentana.job.domain.IndustryType;
import com.sentana.job.dto.response.CompanyResponse;
import com.sentana.job.mapper.CompanyMapper;
import com.sentana.job.models.entities.Company;
import com.sentana.job.models.entities.SocialLink;
import com.sentana.job.models.requests.CompanyRequest;
import com.sentana.job.models.responses.SocialLinkResponse;
import com.sentana.job.repositories.CompanyRepository;
import com.sentana.job.services.CompanyService;
import com.sentana.job.utils.GenerateUniqueSlug;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImplement implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public CompanyResponse create(Long ownerId, CompanyRequest request) throws Exception {
        if (companyRepository.existsByOwnerId(ownerId)){
            throw new Exception("you already have a company registered. only one company per account is allowed");
        }
        if (companyRepository.existsByCompanyName(request.getCompanyName())){
            throw new Exception("company already exists. choose a different name");
        }
        if (request.getRegistrationNumber() != null){
            throw new Exception("company already exists. please choose a different registration number");
        }

        String slug = generateUniqueSlug(request.getCompanyName());
        Company company = Company.builder()
                .companyName(request.getCompanyName())
                .slug(slug)
                .tagline(request.getTagline())
                .description(request.getDescription())
                .logoUrl(request.getLogoUrl())
                .coverImageUrl(request.getCoverImageUrl())
                .website(request.getWebsite())
                .email(request.getEmail())
                .phone(request.getPhone())
                .foundedYear(request.getFoundedYear())
                .companySize(request.getCompanySize())
                .companyType(request.getCompanyType())
                .industryType(request.getIndustryType())
                .registrationNumber(request.getRegistrationNumber())
                .ownerId(request.getOwnerId())
                .socialLinks(mapSocialLinks(request.getSocialLinks()))
                .build();

        Company save = companyRepository.save(company);
        return CompanyMapper.toResponse(save);
    }

    @Override
    public CompanyResponse getById(Long id) throws Exception {
        Company company = companyRepository.findById(id).orElseThrow(()->new Exception("company by id " + id + "not found"));
        return CompanyMapper.toResponse(company);
    }

    @Override
    public CompanyResponse getMyCompany(Long ownerId) throws Exception {
        Company company = companyRepository.findByOwnerId(ownerId).orElseThrow(()->new Exception(""));
        return CompanyMapper.toResponse(company);
    }

    @Override
    public List<CompanyResponse> gets(CompanyType companyType, IndustryType industryType, CompanyStatus companyStatus) {

        return companyRepository.findByFilter(companyType, industryType, companyStatus)
                .stream().map(CompanyMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public CompanyResponse update(Long companyId, Long ownerId, CompanyRequest request) throws Exception {
        Company company = getCompanyEntityById(companyId);
        if(!company.getCompanyName().equals(request.getCompanyName())&&companyRepository.existsByCompanyName(request.getCompanyName())){
            throw new Exception("company already exists. please choose a different name");
        }
        if(request.getRegistrationNumber()!=null
                && !request.getRegistrationNumber().equals(company.getRegistrationNumber())
                && companyRepository.existsByRegistrationNumber(request.getRegistrationNumber())){
            throw new Exception("company already exists. please choose a different number");
        }
        company.setCompanyName(request.getCompanyName());
        company.setTagline(request.getTagline());
        company.setDescription(request.getDescription());
        company.setLogoUrl(request.getLogoUrl());
        company.setCoverImageUrl(request.getCoverImageUrl());
        company.setWebsite(request.getWebsite());
        company.setEmail(request.getEmail());
        company.setPhone(request.getPhone());
        company.setFoundedYear(request.getFoundedYear());
        company.setCompanySize(request.getCompanySize());
        company.setCompanyType(request.getCompanyType());
        company.setIndustryType(request.getIndustryType());
        company.setRegistrationNumber(request.getRegistrationNumber());
        company.setSocialLinks(mapSocialLinks(request.getSocialLinks()));

        return CompanyMapper.toResponse(companyRepository.save(company));
    }

    @Override
    public CompanyResponse verify(Long companyId) throws Exception {
        Company company = getCompanyEntityById(companyId);
        company.setCompanyStatus(CompanyStatus.ACTIVE);
        company.setVerified(true);
        return CompanyMapper.toResponse(companyRepository.save(company));
    }

    @Override
    public void delete(Long companyId, Long ownerId) throws Exception {
        Company company = getCompanyEntityById(companyId);
        assertOwner(company, ownerId);
        companyRepository.delete(company);
    }

    @Override
    public CompanyResponse deactivateCompany(Long companyId) throws Exception {
        Company company = getCompanyEntityById(companyId);
        company.setCompanyStatus(CompanyStatus.SUSPENDED);
        company.setVerified(false);
        return CompanyMapper.toResponse(companyRepository.save(company));
    }

    @Override
    public Company getCompanyEntityById(Long companyId) throws Exception {
        return companyRepository.findById(companyId).orElseThrow(()->new Exception("company by id " + companyId + "not found"));
    }

    /**
     *
     */

    private void assertOwner(Company company, Long ownerId) throws Exception {
        if(!company.getOwnerId().equals(ownerId)){
            throw new Exception("you are not the owner of this company");
        }
    }

    private List<SocialLink> mapSocialLinks(List<SocialLinkResponse> socialLinks) {
        if (socialLinks == null || socialLinks.isEmpty()){
            return new ArrayList<SocialLink>();
        }
        return socialLinks.stream().map(e -> SocialLink.builder()
                        .platform(e.getPlatform())
                        .url(e.getUrl())
                        .build())
                .collect(Collectors.toList());
    }

    private String generateUniqueSlug (String name){
        String base = GenerateUniqueSlug.execute(name);
        if(!companyRepository.existsBySlug(base)){
            return base;
        }
        int counter=1;
        while (companyRepository.existsBySlug(base + "-"+counter)){
            counter++;
        }
        return base+"-"+counter;
    }

}
