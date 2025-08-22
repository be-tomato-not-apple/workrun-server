package com.example.demo.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 복지정책 목록 조회용 DTO
 * 전체 조회 시 필요한 핵심 정보만 포함
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WelfareSummaryDTO {
    private Long id;
    private String center;
    private String serviceName;
    private String content;
    private String target;
    private List<String> homeStatusList;
    private List<String> topicList;
}
