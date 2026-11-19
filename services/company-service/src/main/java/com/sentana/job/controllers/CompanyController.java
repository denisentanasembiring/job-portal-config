package com.sentana.job.controllers;

import com.sentana.job.domain.CompanyStatus;
import com.sentana.job.domain.CompanyType;
import com.sentana.job.domain.IndustryType;
import com.sentana.job.dto.response.ApiResponse;
import com.sentana.job.dto.response.CompanyResponse;
import com.sentana.job.models.requests.CompanyRequest;
import com.sentana.job.services.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/companies")
public class CompanyController {
    private final CompanyService  companyService;

    public ResponseEntity<CompanyResponse> create(
            @RequestHeader("X-User-Id") Long ownerId,
            @RequestBody @Valid CompanyRequest companyRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.create(ownerId, companyRequest));
    }

    @GetMapping(path = "/id")
    public ResponseEntity<CompanyResponse> getCompanyById( @PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getById(id));
    }

    @GetMapping(path = "/my")
    public ResponseEntity<CompanyResponse> getMyCompany( @RequestHeader("X-User-Id") Long ownerId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getMyCompany(ownerId));
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAll(
            @RequestParam(required = false)CompanyType companyType,
            @RequestParam(required = false) IndustryType industryType,
            @RequestParam(required = false) CompanyStatus companyStatus
            ){
        return ResponseEntity.ok(companyService.gets(companyType, industryType, companyStatus));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CompanyResponse> update(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long ownerId,
            @RequestBody @Valid CompanyRequest companyRequest
    ) throws Exception {
        return ResponseEntity.ok(companyService.update(id, ownerId, companyRequest));
    }

    @PatchMapping("/{id}/verify")
    public ResponseEntity<CompanyResponse> verifyCompany(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(companyService.verify(id));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<CompanyResponse> deactivateCompany(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(companyService.deactivateCompany(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCompany(@PathVariable Long id, @RequestHeader("X-User-Id") Long ownerId) throws Exception {
        companyService.delete(id, ownerId);
        return ResponseEntity.ok(new ApiResponse("company deleted successfully", true));
    }

 }
