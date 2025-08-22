package com.example.demo.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class BookmarkInstitutionResponse {
    private Long id;
    private String name;
    private String institutionTypeName;
    private String address;
    private String homepageUrl;
    private LocalDateTime bookmarkedAt;
}
