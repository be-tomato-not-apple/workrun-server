package com.example.demo.service;

import com.example.demo.domain.Institution;
import com.example.demo.repository.InstitutionRepository;
import com.example.demo.web.dto.InstitutionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 기관 서비스
 * 기관 검색, 조회
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InstitutionService {

    private final InstitutionRepository institutionRepository;

    @Transactional(readOnly = true)
    public Page<InstitutionDto> search(String q, String code, Pageable pageable) {
        Page<Institution> page = institutionRepository.search(q, code, pageable);
        return page.map(InstitutionDto::from);
    }

    @Transactional(readOnly = true)
    public InstitutionDto get(Long id) {
        Institution e = institutionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("기관을 찾을 수 없습니다: id=" + id));
        return InstitutionDto.from(e);
    }
}
