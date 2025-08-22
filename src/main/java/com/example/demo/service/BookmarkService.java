package com.example.demo.service;

import com.example.demo.web.dto.BookmarkCreateRequest;
import com.example.demo.web.dto.BookmarkInstitutionResponse;
import com.example.demo.web.dto.BookmarkWelfareResponse;
import com.example.demo.web.dto.CursorPageResponse;

import java.util.List;

public interface BookmarkService {

    /**
     * 북마크 등록
     */
    void createBookmark(BookmarkCreateRequest request);

    /**
     * 복지 북마크 목록 조회 (기존 방식)
     */
    List<BookmarkWelfareResponse> getWelfareBookmarks(String userUuid);

    /**
     * 기관 북마크 목록 조회 (기존 방식)
     */
    List<BookmarkInstitutionResponse> getInstitutionBookmarks(String userUuid);

    /**
     * 복지 북마크 목록 조회 (Cursor 방식)
     */
    CursorPageResponse<BookmarkWelfareResponse> getWelfareBookmarks(String userUuid, Long cursor, int size);

    /**
     * 기관 북마크 목록 조회 (Cursor 방식)
     */
    CursorPageResponse<BookmarkInstitutionResponse> getInstitutionBookmarks(String userUuid, Long cursor, int size);

    /**
     * 북마크 삭제
     */
    void deleteBookmark(String userUuid, Long bookmarkTypeId, Long targetId);
}
