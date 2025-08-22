package com.example.demo.web;

import com.example.demo.apiPayload.ApiResponse;
import com.example.demo.dto.FilterWelfareRequest;
import com.example.demo.dto.FilteredWelfareDTO;
import com.example.demo.dto.welfareDetailDTO;
import com.example.demo.service.WelfareService;
import com.example.demo.web.dto.WelfareSummaryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "복지정책 API", description = "복지정책 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/welfares")
@Slf4j
public class WelfareController {

    private final WelfareService welfareService;

    @Operation(summary = "복지정책 전체 목록 조회", description = "모든 복지정책 목록을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<Page<WelfareSummaryDTO>>> getAllWelfares(
            @Parameter(description = "페이지 번호 (0부터 시작)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "20") int size) {

        log.info("복지정책 전체 목록 조회 API 호출 - page: {}, size: {}", page, size);

        Page<WelfareSummaryDTO> welfares = welfareService.getAllWelfares(page, size);
        return ResponseEntity.ok(ApiResponse.success(welfares));
    }

    @Operation(summary = "복지정책 전체 목록 조회 (페이징 없음)", description = "모든 복지정책 목록을 한번에 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<WelfareSummaryDTO>>> getAllWelfaresWithoutPaging() {

        log.info("복지정책 전체 목록 조회 API 호출 (페이징 없음)");

        List<WelfareSummaryDTO> welfares = welfareService.getAllWelfares();
        return ResponseEntity.ok(ApiResponse.success(welfares));
    }

    @GetMapping("/{welfareId}")
    @Operation(summary = "id기반 정보조회 api")
    @Parameters({
            @Parameter(name = "welfareId", description = "복지정책 아이디 넘겨주세요")
    })
    public ResponseEntity<ApiResponse<welfareDetailDTO>> getWelfareDetail(
            @PathVariable Long welfareId
    ) {
        log.info("복지정책 상세 조회 API 호출 - welfareId: {}", welfareId);

        welfareDetailDTO result = welfareService.getDetailById(welfareId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("/filtering")
    @Operation(summary = "카테고리의 교집합에 해당하는 복지 정책을 반환합니다")
    public ResponseEntity<ApiResponse<FilteredWelfareDTO.listDTO>> getFilteredWelfare(
            @RequestBody FilterWelfareRequest dto
    ) {
        return null;
    }
}
