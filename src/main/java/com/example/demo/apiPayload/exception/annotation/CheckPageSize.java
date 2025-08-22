package com.example.demo.apiPayload.exception.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import com.example.demo.apiPayload.exception.validator.CheckPageSizeValidator;

@Documented
@Constraint(validatedBy = CheckPageSizeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPageSize {

	String message() default "페이지 크기는 1이상 부터 입력이 가능합니다.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
