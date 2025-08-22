package com.example.demo.service;

import com.example.demo.dto.welfareDetailDTO;
import com.example.demo.web.dto.CursorPageResponse;
import com.example.demo.web.dto.WelfareSummaryDTO;

public interface WelfareService {
    welfareDetailDTO getDetailById(Long id);

    /**
     * 복지정책 목록 조회 (Cursor 방식)
     */
    CursorPageResponse<WelfareSummaryDTO> getWelfares(Long cursor, int size);
}
