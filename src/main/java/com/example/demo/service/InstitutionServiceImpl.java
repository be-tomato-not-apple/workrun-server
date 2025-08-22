package com.example.demo.service;

import com.example.demo.apiPayload.code.error.InstitutionErrorCode;
import com.example.demo.apiPayload.exception.GeneralException;
import com.example.demo.repository.InstitutionRepository;
import com.example.demo.web.dto.InstitutionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 기관 서비스 구현체
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class InstitutionServiceImpl implements InstitutionService {

    private final InstitutionRepository institutionRepository;

    @Override
    public List<InstitutionDto> getInstitutions(String institutionCode) {
        try {
            // null, "", "0" → 전체조회
            String code = (institutionCode == null) ? null : institutionCode.trim();
            if (code != null && (code.isEmpty() || "0".equals(code))) {
                code = null;
            }

            log.info("기관 목록 조회 요청 - institutionCode: {}", code);

            List<InstitutionDto> institutions = institutionRepository.findAllSimple(code);

            log.info("기관 목록 조회 완료 - 조회된 건수: {}", institutions.size());

            return institutions;

        } catch (Exception e) {
            log.error("기관 목록 조회 중 오류 발생", e);
            throw new GeneralException(InstitutionErrorCode.INSTITUTION_NOT_FOUND);
        }
    }
}
