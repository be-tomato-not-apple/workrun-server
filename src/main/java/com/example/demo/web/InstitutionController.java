package com.example.demo.web;

import com.example.demo.service.InstitutionQueryService;
import com.example.demo.web.dto.InstitutionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 기관 관련 API 컨트롤러
 * 기관 목록을 조회하는 엔드포인트를 제공
 */
@RestController
@RequestMapping("/api/institutions")
@RequiredArgsConstructor
public class InstitutionController {

    private final InstitutionQueryService service;

    /**
     * 파라미터가 없거나 "", "0"이면 전체 반환
     * 값이 있으면 해당 institution_code로 필터
     */
    @GetMapping
    public List<InstitutionDto> list(
            @RequestParam(name = "institutionCode", required = false) String institutionCode) {
        return service.list(institutionCode);
    }
}