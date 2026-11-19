package com.sentana.job.models.entities;

import com.sentana.job.domain.ResumeTemplate;
import com.sentana.job.domain.ResumeVisibility;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity(name = "Resume")
@Table(name = "resumes")
@NoArgsConstructor
@AllArgsConstructor
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long candidateId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ResumeTemplate template=ResumeTemplate.PROFESSIONAL;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ResumeVisibility visibility=ResumeVisibility.PUBLIC;

    @Column(nullable = false)
    private Boolean isDefault=false;

    @Embedded
    private PersonalInformation personalInformation;

    private String summary;

    private Integer completionScore =0;

    private Boolean isActive=true;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

}
