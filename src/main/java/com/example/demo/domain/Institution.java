package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 기관 엔티티
 * 기관 이름, 기관 유형, 주소, 홈페이지 URL
 */
@Entity
@Table(name = "institution",
        indexes = {
                @Index(name = "idx_institution_code", columnList = "institution_code"),
                @Index(name = "idx_institution_name", columnList = "name")
        },
        uniqueConstraints = @UniqueConstraint(name = "uq_institution_name_addr", columnNames = {"name", "address"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "institution_code", referencedColumnName = "code", nullable = false)
    private InstitutionType institutionType;

    @Column(nullable = false, length = 300)
    private String address;

    @Column(name = "homepage_url", length = 300)
    private String homepageUrl;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;
}