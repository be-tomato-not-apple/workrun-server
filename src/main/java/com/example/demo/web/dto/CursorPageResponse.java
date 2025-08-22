package com.example.demo.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Cursor 기반 페이징 응답 DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursorPageResponse<T> {
    private List<T> content;
    private Long nextCursor;
    private boolean hasNext;
    private int size;
    
    public static <T> CursorPageResponse<T> of(List<T> content, Long nextCursor, boolean hasNext) {
        return CursorPageResponse.<T>builder()
            .content(content)
            .nextCursor(nextCursor)
            .hasNext(hasNext)
            .size(content.size())
            .build();
    }
}
