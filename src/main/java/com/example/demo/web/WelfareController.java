package com.example.demo.web;

import com.example.demo.apiPayload.ApiResponse;
import com.example.demo.dto.FilterWelfareRequest;
import com.example.demo.dto.FilteredWelfareDTO;
import com.example.demo.dto.welfareDetailDTO;
import com.example.demo.service.WelfareService;
import com.example.demo.web.dto.CursorPageResponse;
import com.example.demo.web.dto.WelfareSummaryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "복지정책 API", description = "복지정책 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/welfares")
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class WelfareController {

    private final WelfareService welfareService;

    @Operation(summary = "복지정책 목록 조회 (Cursor 방식)", description = "Cursor 기반으로 복지정책 목록을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<CursorPageResponse<WelfareSummaryDTO>>> getWelfares(
            @Parameter(description = "커서 (이전 마지막 항목의 ID, 첫 조회시 null)")
            @RequestParam(required = false) Long cursor,
            @Parameter(description = "조회할 개수")
            @RequestParam(defaultValue = "20") int size) {

        log.info("복지정책 목록 조회 API 호출 (Cursor 방식) - cursor: {}, size: {}", cursor, size);

        CursorPageResponse<WelfareSummaryDTO> welfares = welfareService.getWelfares(cursor, size);
        return ResponseEntity.ok(ApiResponse.success(welfares));
    }

    @Operation(summary = "복지정책 전체 목록 조회", description = "모든 복지정책 목록을 한번에 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<WelfareSummaryDTO>>> getAllWelfares() {

        log.info("복지정책 전체 목록 조회 API 호출");

        List<WelfareSummaryDTO> welfares = welfareService.getAllWelfares();
        return ResponseEntity.ok(ApiResponse.success(welfares));
    }

    @GetMapping("/{welfareId}")
    @Operation(summary = "복지정책 상세 조회", description = "ID로 특정 복지정책의 상세 정보를 조회합니다.")
    @Parameters({
            @Parameter(name = "welfareId", description = "복지정책 아이디")
    })
    public ResponseEntity<ApiResponse<welfareDetailDTO>> getWelfareDetail(
            @PathVariable Long welfareId
    ) {
        log.info("복지정책 상세 조회 API 호출 - welfareId: {}", welfareId);

        welfareDetailDTO result = welfareService.getDetailById(welfareId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("/filtering")
    @Operation(summary = "복지정책 필터링 조회", description = "카테고리의 교집합에 해당하는 복지 정책을 반환합니다")
    public ResponseEntity<ApiResponse<FilteredWelfareDTO.listDTO>> getFilteredWelfare(
            @RequestBody FilterWelfareRequest dto
    ) {
        return null;
    }
}
