package com.example.demo.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class BookmarkWelfareResponse {
    private Long id;
    private String center;
    private String serviceName;
    private String content;
    private String url;
    private String target;
    private String applyMethod;
    private String needDocument;
    private List<String> homeStatusList;
    private List<String> topicList;
    private LocalDateTime bookmarkedAt;
}
