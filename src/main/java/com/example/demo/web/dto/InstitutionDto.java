package com.example.demo.web.dto;

import com.example.demo.domain.Institution;
import lombok.Builder;
import lombok.Getter;

/**
 * 기관 DTO
 * 기관 정보, 코드, 유형, 주소, 홈페이지 URL
 */
@Getter
@Builder
public class InstitutionDto {
    private Long id;
    private String name;
    private String institutionCode;
    private String institutionTypeName;
    private String address;
    private String homepageUrl;

    public static InstitutionDto from(Institution e) {
        return InstitutionDto.builder()
                .id(e.getId())
                .name(e.getName())
                .institutionCode(e.getInstitutionType().getCode())
                .institutionTypeName(e.getInstitutionType().getDisplayName())
                .address(e.getAddress())
                .homepageUrl(e.getHomepageUrl())
                .build();
    }
}
