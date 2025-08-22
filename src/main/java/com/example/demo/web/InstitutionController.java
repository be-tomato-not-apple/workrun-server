package com.example.demo.web;

import com.example.demo.service.InstitutionService;
import com.example.demo.web.dto.InstitutionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

/**
 * 기관 정보 API
 * 기관 목록 검색, 상세 조회
 */
@RestController
@RequestMapping("/api/institutions")
@RequiredArgsConstructor
public class InstitutionController {

    private final InstitutionService service;

    // 목록 검색: q, institutionCode, 정렬/페이지
    @GetMapping
    public Page<InstitutionDto> search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String institutionCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "name,asc") String sort
    ) {
        Sort sortSpec = parseSort(sort); // name,asc | updatedAt,desc ...
        Pageable pageable = PageRequest.of(page, size, sortSpec);
        return service.search(q, institutionCode, pageable);
    }

    // 상세
    @GetMapping("/{id}")
    public InstitutionDto get(@PathVariable Long id) {
        return service.get(id);
    }

    private Sort parseSort(String sort) {
        // "name,asc" or "updatedAt,desc"
        String[] p = sort.split(",", 2);
        String prop = p[0];
        Sort.Direction dir = (p.length > 1 && "desc".equalsIgnoreCase(p[1])) ? Sort.Direction.DESC : Sort.Direction.ASC;

        // 엔티티 필드명 매핑
        return switch (prop) {
            case "name" -> Sort.by(dir, "name", "id");
            case "createdAt" -> Sort.by(dir, "createdAt", "id");
            case "updatedAt" -> Sort.by(dir, "updatedAt", "id");
            default -> Sort.by(Sort.Direction.ASC, "name", "id");
        };
    }
}
