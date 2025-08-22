package com.example.demo.repository;

import com.example.demo.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    
    @Query("SELECT b FROM Bookmark b JOIN FETCH b.bookmarkType WHERE b.anonymousUser.id = :anonymousUserId AND b.bookmarkType.id = :bookmarkTypeId")
    List<Bookmark> findByAnonymousUserIdAndBookmarkTypeId(@Param("anonymousUserId") Long anonymousUserId, @Param("bookmarkTypeId") Long bookmarkTypeId);
    
    boolean existsByAnonymousUserIdAndBookmarkTypeIdAndTargetId(Long anonymousUserId, Long bookmarkTypeId, Long targetId);
    
    void deleteByAnonymousUserIdAndBookmarkTypeIdAndTargetId(Long anonymousUserId, Long bookmarkTypeId, Long targetId);
    
    @Query("SELECT b FROM Bookmark b JOIN FETCH b.bookmarkType WHERE b.anonymousUser.uuid = :uuid AND b.bookmarkType.id = :bookmarkTypeId")
    List<Bookmark> findByUserUuidAndBookmarkTypeId(@Param("uuid") String uuid, @Param("bookmarkTypeId") Long bookmarkTypeId);
}
