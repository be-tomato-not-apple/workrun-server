package com.example.demo.service;

import com.example.demo.repository.InstitutionRepository;
import com.example.demo.web.dto.InstitutionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 기관 조회 서비스
 * 기관 코드에 따라 기관 목록을 조회하는 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InstitutionQueryService {
    private final InstitutionRepository repo;

    public List<InstitutionDto> list(String institutionCodeParam) {
        // null, "", "0" → 전체조회
        String code = (institutionCodeParam == null) ? null : institutionCodeParam.trim();
        if (code != null && (code.isEmpty() || "0".equals(code))) code = null;
        return repo.findAllSimple(code);
    }
}