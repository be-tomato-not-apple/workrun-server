package com.example.demo.service;

import com.example.demo.dto.FilterWelfareRequest;
import com.example.demo.dto.FilteredWelfareDTO;
import com.example.demo.dto.welfareDetailDTO;
import com.example.demo.web.dto.CursorPageResponse;
import com.example.demo.web.dto.WelfareSummaryDTO;

import java.util.List;

public interface WelfareService {
    welfareDetailDTO getDetailById(Long id);

    /**
     * 복지정책 목록 조회 (Cursor 방식)
     */
    CursorPageResponse<WelfareSummaryDTO> getWelfares(Long cursor, int size);

    /**
     * 복지정책 전체 목록 조회 (페이징 없음)
     */
    List<WelfareSummaryDTO> getAllWelfares();

    // 복지 정책 필터링 목록 조회 (Cursor 방식)
    CursorPageResponse<FilteredWelfareDTO.welfarePreview> getFilteredWelfares(Long cursor, int size, FilterWelfareRequest dto);
}
