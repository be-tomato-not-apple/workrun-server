package com.example.demo.web;

import com.example.demo.apiPayload.ApiResponse;
import com.example.demo.service.InstitutionService;
import com.example.demo.web.dto.InstitutionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 기관 관련 API 컨트롤러
 * 기관 목록을 조회하는 엔드포인트를 제공
 */
@Tag(name = "기관 API", description = "기관 관련 API")
@RestController
@RequestMapping("/api/institutions")
@RequiredArgsConstructor
@Slf4j
public class InstitutionController {

    private final InstitutionService institutionService;

    /**
     * 기관 목록 조회
     * 파라미터가 없거나 "", "0"이면 전체 반환
     * 값이 있으면 해당 institution_code로 필터
     */
    @Operation(summary = "기관 목록 조회", description = "기관 코드에 따라 기관 목록을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "기관을 찾을 수 없음"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping
    public ApiResponse<List<InstitutionDto>> getInstitutions(
            @Parameter(description = "기관 코드 (없으면 전체 조회)")
            @RequestParam(name = "institutionCode", required = false) String institutionCode) {

        log.info("기관 목록 조회 API 호출 - institutionCode: {}", institutionCode);

        List<InstitutionDto> institutions = institutionService.getInstitutions(institutionCode);
        return ApiResponse.success(institutions);
    }
}