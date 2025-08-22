package com.example.demo.apiPayload.code.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum InstitutionErrorCode implements ErrorCode {

    INSTITUTION_NOT_FOUND(HttpStatus.NOT_FOUND, "INSTITUTION001", "요청한 기관을 찾을 수 없습니다."),
    INVALID_INSTITUTION_CODE(HttpStatus.BAD_REQUEST, "INSTITUTION002", "유효하지 않은 기관 코드입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
