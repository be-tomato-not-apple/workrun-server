package com.example.demo.service;

import com.example.demo.apiPayload.code.error.InstitutionErrorCode;
import com.example.demo.apiPayload.exception.GeneralException;
import com.example.demo.repository.InstitutionRepository;
import com.example.demo.web.dto.CursorPageResponse;
import com.example.demo.web.dto.InstitutionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public CursorPageResponse<InstitutionDto> getInstitutions(String institutionCode, Long cursor, int size) {
        try {
            // null, "", "0" → 전체조회
            String code = (institutionCode == null) ? null : institutionCode.trim();
            if (code != null && (code.isEmpty() || "0".equals(code))) {
                code = null;
            }

            log.info("기관 목록 조회 요청 (Cursor 방식) - institutionCode: {}, cursor: {}, size: {}", code, cursor, size);

            // size + 1로 조회해서 다음 페이지 존재 여부 확인
            Pageable pageable = PageRequest.of(0, size + 1);
            List<InstitutionDto> institutions = institutionRepository.findWithCursor(code, cursor, pageable);

            // 다음 페이지 존재 여부 확인
            boolean hasNext = institutions.size() > size;
            if (hasNext) {
                institutions.remove(institutions.size() - 1); // 마지막 요소 제거
            }

            // 다음 커서 설정
            Long nextCursor = hasNext && !institutions.isEmpty()
                    ? institutions.get(institutions.size() - 1).getId()
                    : null;

            log.info("기관 목록 조회 완료 (Cursor 방식) - 조회된 건수: {}, hasNext: {}", institutions.size(), hasNext);

            return CursorPageResponse.of(institutions, nextCursor, hasNext);

        } catch (Exception e) {
            log.error("기관 목록 조회 중 오류 발생 (Cursor 방식)", e);
            throw new GeneralException(InstitutionErrorCode.INSTITUTION_NOT_FOUND);
        }
    }
}
