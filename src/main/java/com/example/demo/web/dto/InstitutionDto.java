package com.example.demo.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 기관 DTO
 * 기관 ID, 이름, 기관 유형 이름, 주소, 홈페이지 URL을 포함
 */
@Getter
@AllArgsConstructor
public class InstitutionDto {
    private Long id;
    private String name;
    private String institutionTypeName; // 기관구분(한글)
    private String address;
    private String homepageUrl;
}
