package com.example.demo.apiPayload.code;

import org.springframework.http.HttpStatus;

public interface SuccessCode {

	HttpStatus getHttpStatus();

	String getCode();

	String getMessage();
}
