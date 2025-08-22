package com.example.demo.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkCreateRequest {
    
    @NotBlank(message = "사용자 UUID는 필수입니다")
    private String userUuid;
    
    @NotNull(message = "북마크 타입 ID는 필수입니다")
    private Long bookmarkTypeId;  // 1: WELFARE, 2: INSTITUTION
    
    @NotNull(message = "대상 ID는 필수입니다")
    private Long targetId;
}
