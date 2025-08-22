package com.example.demo.service;

import com.example.demo.apiPayload.code.error.BookmarkErrorCode;
import com.example.demo.apiPayload.exception.GeneralException;
import com.example.demo.domain.AnonymousUser;
import com.example.demo.domain.Bookmark;
import com.example.demo.domain.BookmarkType;
import com.example.demo.domain.Welfare;
import com.example.demo.repository.BookmarkRepository;
import com.example.demo.repository.BookmarkTypeRepository;
import com.example.demo.repository.InstitutionRepository;
import com.example.demo.repository.WelfareRepository;
import com.example.demo.web.dto.BookmarkCreateRequest;
import com.example.demo.web.dto.BookmarkInstitutionResponse;
import com.example.demo.web.dto.BookmarkWelfareResponse;
import com.example.demo.web.dto.InstitutionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BookmarkServiceImpl implements BookmarkService {
    
    private final BookmarkRepository bookmarkRepository;
    private final BookmarkTypeRepository bookmarkTypeRepository;
    private final AnonymousUserService anonymousUserService;
    private final WelfareRepository welfareRepository;
    private final InstitutionRepository institutionRepository;
    
    private static final Long WELFARE_TYPE_ID = 1L;
    private static final Long INSTITUTION_TYPE_ID = 2L;
    
    @Override
    @Transactional
    public void createBookmark(BookmarkCreateRequest request) {
        log.info("북마크 등록 요청 - UUID: {}, TypeId: {}, TargetId: {}", 
                request.getUserUuid(), request.getBookmarkTypeId(), request.getTargetId());
        
        // 사용자 조회 또는 생성
        AnonymousUser user = anonymousUserService.getOrCreateUser(request.getUserUuid());
        
        // 북마크 타입 검증
        BookmarkType bookmarkType = bookmarkTypeRepository.findById(request.getBookmarkTypeId())
            .orElseThrow(() -> new GeneralException(BookmarkErrorCode.INVALID_BOOKMARK_TYPE));
        
        // 중복 북마크 체크
        if (bookmarkRepository.existsByAnonymousUserIdAndBookmarkTypeIdAndTargetId(
                user.getId(), request.getBookmarkTypeId(), request.getTargetId())) {
            throw new GeneralException(BookmarkErrorCode.BOOKMARK_ALREADY_EXISTS);
        }
        
        // 대상 존재 여부 검증
        validateTargetExists(request.getBookmarkTypeId(), request.getTargetId());
        
        // 북마크 저장
        Bookmark bookmark = Bookmark.builder()
            .anonymousUser(user)
            .bookmarkType(bookmarkType)
            .targetId(request.getTargetId())
            .build();
        
        bookmarkRepository.save(bookmark);
        log.info("북마크 등록 완료 - BookmarkId: {}", bookmark.getId());
    }
    
    @Override
    public List<BookmarkWelfareResponse> getWelfareBookmarks(String userUuid) {
        log.info("복지 북마크 목록 조회 - UUID: {}", userUuid);
        
        List<Bookmark> bookmarks = bookmarkRepository.findByUserUuidAndBookmarkTypeId(userUuid, WELFARE_TYPE_ID);
        
        return bookmarks.stream()
            .map(bookmark -> {
                Welfare welfare = welfareRepository.findByIdWithDetails(bookmark.getTargetId())
                    .orElseThrow(() -> new GeneralException(BookmarkErrorCode.TARGET_NOT_FOUND));
                
                // homeStatusList 생성
                List<String> homeStatusList = welfare.getHomeStatusList().stream()
                    .map(whs -> whs.getHomeStatus().getContent())
                    .collect(Collectors.toList());
                
                // topicList 생성
                List<String> topicList = welfare.getInterestList().stream()
                    .map(wit -> wit.getInterestTopic().getContent())
                    .collect(Collectors.toList());
                
                return BookmarkWelfareResponse.builder()
                    .id(welfare.getId())
                    .center(welfare.getCenter())
                    .serviceName(welfare.getServiceName())
                    .content(welfare.getContent())
                    .url(welfare.getUrl())
                    .target(welfare.getTarget())
                    .applyMethod(welfare.getApplyMethod())
                    .needDocument(welfare.getNeedDocument())
                    .homeStatusList(homeStatusList)
                    .topicList(topicList)
                    .bookmarkedAt(bookmark.getCreatedAt())
                    .build();
            })
            .collect(Collectors.toList());
    }
    
    @Override
    public List<BookmarkInstitutionResponse> getInstitutionBookmarks(String userUuid) {
        log.info("기관 북마크 목록 조회 - UUID: {}", userUuid);
        
        List<Bookmark> bookmarks = bookmarkRepository.findByUserUuidAndBookmarkTypeId(userUuid, INSTITUTION_TYPE_ID);
        
        return bookmarks.stream()
            .map(bookmark -> {
                InstitutionDto institution = institutionRepository.findSimpleById(bookmark.getTargetId())
                    .orElseThrow(() -> new GeneralException(BookmarkErrorCode.TARGET_NOT_FOUND));
                
                return BookmarkInstitutionResponse.builder()
                    .id(institution.getId())
                    .name(institution.getName())
                    .institutionTypeName(institution.getInstitutionTypeName())
                    .address(institution.getAddress())
                    .homepageUrl(institution.getHomepageUrl())
                    .bookmarkedAt(bookmark.getCreatedAt())
                    .build();
            })
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public void deleteBookmark(String userUuid, Long bookmarkTypeId, Long targetId) {
        log.info("북마크 삭제 요청 - UUID: {}, TypeId: {}, TargetId: {}", userUuid, bookmarkTypeId, targetId);
        
        AnonymousUser user = anonymousUserService.getOrCreateUser(userUuid);
        
        bookmarkRepository.deleteByAnonymousUserIdAndBookmarkTypeIdAndTargetId(
            user.getId(), bookmarkTypeId, targetId);
        
        log.info("북마크 삭제 완료");
    }
    
    private void validateTargetExists(Long bookmarkTypeId, Long targetId) {
        if (WELFARE_TYPE_ID.equals(bookmarkTypeId)) {
            if (!welfareRepository.existsById(targetId)) {
                throw new GeneralException(BookmarkErrorCode.TARGET_NOT_FOUND);
            }
        } else if (INSTITUTION_TYPE_ID.equals(bookmarkTypeId)) {
            if (!institutionRepository.existsById(targetId)) {
                throw new GeneralException(BookmarkErrorCode.TARGET_NOT_FOUND);
            }
        }
    }
}
