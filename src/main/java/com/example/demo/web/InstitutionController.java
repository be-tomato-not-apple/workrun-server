package com.example.demo.web;

import com.example.demo.apiPayload.ApiResponse;
import com.example.demo.service.InstitutionService;
import com.example.demo.web.dto.CursorPageResponse;
import com.example.demo.web.dto.InstitutionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
@CrossOrigin(origins = "http://localhost:3000")
public class InstitutionController {

    private final InstitutionService institutionService;

    /**
     * 기관 목록 조회 (Cursor 방식)
     */
    @Operation(summary = "기관 목록 조회 (Cursor 방식)", description = "Cursor 기반으로 기관 목록을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "기관을 찾을 수 없음"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping
    public ApiResponse<CursorPageResponse<InstitutionDto>> getInstitutions(
            @Parameter(description = "기관 코드 (없으면 전체 조회)")
            @RequestParam(name = "institutionCode", required = false) String institutionCode,
            @Parameter(description = "커서 (이전 마지막 항목의 ID, 첫 조회시 null)")
            @RequestParam(required = false) Long cursor,
            @Parameter(description = "조회할 개수")
            @RequestParam(defaultValue = "20") int size) {

        log.info("기관 목록 조회 API 호출 (Cursor 방식) - institutionCode: {}, cursor: {}, size: {}", institutionCode, cursor, size);

        CursorPageResponse<InstitutionDto> institutions = institutionService.getInstitutions(institutionCode, cursor, size);
        return ApiResponse.success(institutions);
    }

    /**
     * 기관 목록 조회 (기존 방식 - 전체 조회)
     */
    @Operation(summary = "기관 목록 조회 (전체)", description = "모든 기관 목록을 한번에 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "기관을 찾을 수 없음"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/all")
    public ApiResponse<List<InstitutionDto>> getAllInstitutions(
            @Parameter(description = "기관 코드 (없으면 전체 조회)")
            @RequestParam(name = "institutionCode", required = false) String institutionCode) {

        log.info("기관 목록 조회 API 호출 (전체) - institutionCode: {}", institutionCode);

        List<InstitutionDto> institutions = institutionService.getInstitutions(institutionCode);
        return ApiResponse.success(institutions);
    }
}
