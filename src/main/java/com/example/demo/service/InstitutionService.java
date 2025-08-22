package com.example.demo.service;

import com.example.demo.web.dto.CursorPageResponse;
import com.example.demo.web.dto.InstitutionDto;

import java.util.List;

/**
 * 기관 서비스 인터페이스
 */
public interface InstitutionService {

    /**
     * 기관 목록 조회 (기존 방식)
     *
     * @param institutionCode 기관 코드 (null, "", "0"인 경우 전체 조회)
     * @return 기관 목록
     */
    List<InstitutionDto> getInstitutions(String institutionCode);

    /**
     * 기관 목록 조회 (Cursor 방식)
     *
     * @param institutionCode 기관 코드 (null, "", "0"인 경우 전체 조회)
     * @param cursor          커서 (이전 마지막 항목의 ID)
     * @param size            조회할 개수
     * @return 기관 목록 (Cursor 방식)
     */
    CursorPageResponse<InstitutionDto> getInstitutions(String institutionCode, Long cursor, int size);
}
