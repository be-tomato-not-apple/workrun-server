package com.example.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

/**
 * 기관 유형 엔티티
 * 코드, 이름
 */
@Entity
@Table(name = "institution_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstitutionType {
    @Id
    @Column(length = 32)
    private String code;

    @Column(nullable = false, length = 64)
    private String displayName;
}