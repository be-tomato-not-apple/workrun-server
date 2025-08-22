package com.example.demo.service;

import com.example.demo.web.dto.InstitutionDto;

import java.util.List;

/**
 * 기관 서비스 인터페이스
 */
public interface InstitutionService {

    /**
     * 기관 목록 조회
     *
     * @param institutionCode 기관 코드 (null, "", "0"인 경우 전체 조회)
     * @return 기관 목록
     */
    List<InstitutionDto> getInstitutions(String institutionCode);
}
