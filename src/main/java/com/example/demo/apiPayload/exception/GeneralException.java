package com.example.demo.apiPayload.exception;

import com.example.demo.apiPayload.code.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GeneralException extends RuntimeException {

	private final ErrorCode errorCode;
}
