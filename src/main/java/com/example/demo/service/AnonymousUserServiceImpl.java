package com.example.demo.service;

import com.example.demo.domain.AnonymousUser;
import com.example.demo.repository.AnonymousUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnonymousUserServiceImpl implements AnonymousUserService {
    
    private final AnonymousUserRepository anonymousUserRepository;
    
    @Override
    @Transactional
    public AnonymousUser getOrCreateUser(String uuid) {
        log.info("익명 사용자 조회 또는 생성 - UUID: {}", uuid);
        
        return anonymousUserRepository.findByUuid(uuid)
            .map(user -> {
                log.info("기존 사용자 발견 - ID: {}", user.getId());
                // 마지막 접근 시간은 @UpdateTimestamp가 자동 처리
                return user;
            })
            .orElseGet(() -> {
                log.info("새 사용자 생성 - UUID: {}", uuid);
                return anonymousUserRepository.save(
                    AnonymousUser.builder()
                        .uuid(uuid)
                        .build()
                );
            });
    }
}
