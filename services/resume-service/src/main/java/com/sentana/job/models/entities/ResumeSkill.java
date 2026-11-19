package com.sentana.job.models.entities;

import com.sentana.job.domain.ProficiencyLevel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity(name = "ResumeSkill")
@Table(name = "resume_skills")
@NoArgsConstructor
@AllArgsConstructor
public class ResumeSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Resume resume;

    @Column(nullable = false)
    private String skillName;

    private ProficiencyLevel proficiencyLevel=ProficiencyLevel.BEGINNER;

    private Integer yearOfExperience;

    private Integer displayOrder=0;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
