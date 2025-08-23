package com.example.demo.web;

import com.example.demo.apiPayload.ApiResponse;
import com.example.demo.service.BookmarkService;
import com.example.demo.web.dto.BookmarkCreateRequest;
import com.example.demo.web.dto.BookmarkInstitutionResponse;
import com.example.demo.web.dto.BookmarkWelfareResponse;
import com.example.demo.web.dto.CursorPageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "북마크 API", description = "북마크 관련 API")
@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Operation(summary = "북마크 등록", description = "복지정책 또는 지원센터를 북마크에 등록합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "이미 등록된 북마크"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping
    public ApiResponse<Void> createBookmark(@Valid @RequestBody BookmarkCreateRequest request) {
        log.info("북마크 등록 API 호출 - UUID: {}, TypeId: {}, TargetId: {}",
                request.getUserUuid(), request.getBookmarkTypeId(), request.getTargetId());

        bookmarkService.createBookmark(request);
        return ApiResponse.successWithNoData();
    }

    @Operation(summary = "복지정책 북마크 목록 조회 (Cursor 방식)", description = "Cursor 기반으로 사용자의 복지정책 북마크 목록을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/welfare")
    public ApiResponse<CursorPageResponse<BookmarkWelfareResponse>> getWelfareBookmarks(
            @Parameter(description = "사용자 UUID")
            @RequestParam String userUuid,
            @Parameter(description = "커서 (이전 마지막 항목의 ID, 첫 조회시 null)")
            @RequestParam(required = false) Long cursor,
            @Parameter(description = "조회할 개수")
            @RequestParam(defaultValue = "20") int size) {

        log.info("복지정책 북마크 목록 조회 API 호출 (Cursor 방식) - UUID: {}, cursor: {}, size: {}", userUuid, cursor, size);

        CursorPageResponse<BookmarkWelfareResponse> bookmarks = bookmarkService.getWelfareBookmarks(userUuid, cursor, size);
        return ApiResponse.success(bookmarks);
    }

    @Operation(summary = "지원센터 북마크 목록 조회 (Cursor 방식)", description = "Cursor 기반으로 사용자의 지원센터 북마크 목록을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/institution")
    public ApiResponse<CursorPageResponse<BookmarkInstitutionResponse>> getInstitutionBookmarks(
            @Parameter(description = "사용자 UUID")
            @RequestParam String userUuid,
            @Parameter(description = "커서 (이전 마지막 항목의 ID, 첫 조회시 null)")
            @RequestParam(required = false) Long cursor,
            @Parameter(description = "조회할 개수")
            @RequestParam(defaultValue = "20") int size) {

        log.info("지원센터 북마크 목록 조회 API 호출 (Cursor 방식) - UUID: {}, cursor: {}, size: {}", userUuid, cursor, size);

        CursorPageResponse<BookmarkInstitutionResponse> bookmarks = bookmarkService.getInstitutionBookmarks(userUuid, cursor, size);
        return ApiResponse.success(bookmarks);
    }

    @Operation(summary = "복지정책 북마크 목록 조회 (전체)", description = "사용자의 복지정책 북마크 목록을 한번에 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/welfare/all")
    public ApiResponse<List<BookmarkWelfareResponse>> getAllWelfareBookmarks(
            @Parameter(description = "사용자 UUID")
            @RequestParam String userUuid) {

        log.info("복지정책 북마크 목록 조회 API 호출 (전체) - UUID: {}", userUuid);

        List<BookmarkWelfareResponse> bookmarks = bookmarkService.getWelfareBookmarks(userUuid);
        return ApiResponse.success(bookmarks);
    }

    @Operation(summary = "지원센터 북마크 목록 조회 (전체)", description = "사용자의 지원센터 북마크 목록을 한번에 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/institution/all")
    public ApiResponse<List<BookmarkInstitutionResponse>> getAllInstitutionBookmarks(
            @Parameter(description = "사용자 UUID")
            @RequestParam String userUuid) {

        log.info("지원센터 북마크 목록 조회 API 호출 (전체) - UUID: {}", userUuid);

        List<BookmarkInstitutionResponse> bookmarks = bookmarkService.getInstitutionBookmarks(userUuid);
        return ApiResponse.success(bookmarks);
    }

    @Operation(summary = "북마크 삭제", description = "특정 북마크를 삭제합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "북마크를 찾을 수 없음"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @DeleteMapping
    public ApiResponse<Void> deleteBookmark(
            @Parameter(description = "사용자 UUID")
            @RequestParam String userUuid,
            @Parameter(description = "북마크 타입 ID (1: 복지정책, 2: 지원센터)")
            @RequestParam Long bookmarkTypeId,
            @Parameter(description = "대상 ID")
            @RequestParam Long targetId) {

        log.info("북마크 삭제 API 호출 - UUID: {}, TypeId: {}, TargetId: {}", userUuid, bookmarkTypeId, targetId);

        bookmarkService.deleteBookmark(userUuid, bookmarkTypeId, targetId);
        return ApiResponse.successWithNoData();
    }
}
