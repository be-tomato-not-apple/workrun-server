package com.example.demo.service;

import com.example.demo.dto.welfareDetailDTO;
import com.example.demo.web.dto.WelfareSummaryDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WelfareService {
    welfareDetailDTO getDetailById(Long id);

    /**
     * 복지정책 전체 목록 조회 (페이징)
     */
    Page<WelfareSummaryDTO> getAllWelfares(int page, int size);

    /**
     * 복지정책 전체 목록 조회 (전체)
     */
    List<WelfareSummaryDTO> getAllWelfares();
}
