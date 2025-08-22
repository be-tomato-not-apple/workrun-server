package com.example.demo.service;

import com.example.demo.domain.AnonymousUser;

public interface AnonymousUserService {
    
    /**
     * UUID로 사용자 조회 또는 생성
     */
    AnonymousUser getOrCreateUser(String uuid);
}
