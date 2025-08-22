package com.example.demo.apiPayload.code.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BookmarkErrorCode implements ErrorCode {

    BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "BOOKMARK001", "북마크를 찾을 수 없습니다."),
    BOOKMARK_ALREADY_EXISTS(HttpStatus.CONFLICT, "BOOKMARK002", "이미 북마크에 등록된 항목입니다."),
    INVALID_BOOKMARK_TYPE(HttpStatus.BAD_REQUEST, "BOOKMARK003", "유효하지 않은 북마크 타입입니다."),
    TARGET_NOT_FOUND(HttpStatus.NOT_FOUND, "BOOKMARK004", "북마크 대상을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "BOOKMARK005", "사용자를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
