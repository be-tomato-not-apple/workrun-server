package com.example.demo.apiPayload.code;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

	HttpStatus getHttpStatus();

	String getCode();

	String getMessage();
}
