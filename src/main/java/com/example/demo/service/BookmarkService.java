package com.example.demo.service;

import com.example.demo.web.dto.BookmarkCreateRequest;
import com.example.demo.web.dto.BookmarkInstitutionResponse;
import com.example.demo.web.dto.BookmarkWelfareResponse;

import java.util.List;

public interface BookmarkService {
    
    /**
     * 북마크 등록
     */
    void createBookmark(BookmarkCreateRequest request);
    
    /**
     * 복지 북마크 목록 조회
     */
    List<BookmarkWelfareResponse> getWelfareBookmarks(String userUuid);
    
    /**
     * 기관 북마크 목록 조회
     */
    List<BookmarkInstitutionResponse> getInstitutionBookmarks(String userUuid);
    
    /**
     * 북마크 삭제
     */
    void deleteBookmark(String userUuid, Long bookmarkTypeId, Long targetId);
}
