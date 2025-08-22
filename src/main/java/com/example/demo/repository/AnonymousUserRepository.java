package com.example.demo.repository;

import com.example.demo.domain.AnonymousUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AnonymousUserRepository extends JpaRepository<AnonymousUser, Long> {
    
    Optional<AnonymousUser> findByUuid(String uuid);
    
    // 30일 이상 미접근 사용자 조회 (배치 삭제용)
    @Query("SELECT au FROM AnonymousUser au WHERE au.lastAccessedAt < :cutoffDate")
    List<AnonymousUser> findInactiveUsers(@Param("cutoffDate") LocalDateTime cutoffDate);
}
