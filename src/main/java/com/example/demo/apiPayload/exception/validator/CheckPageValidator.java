package com.example.demo.apiPayload.exception.validator;


import com.example.demo.apiPayload.code.error.CommonErrorCode;
import com.example.demo.apiPayload.exception.annotation.CheckPage;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CheckPageValidator implements ConstraintValidator<CheckPage, Integer> {

	@Override
	public void initialize(CheckPage constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(Integer page, ConstraintValidatorContext context) {
		boolean isValid = page >= 1;
		if (!isValid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(CommonErrorCode.PAGE_UNDER_ONE.getMessage())
				.addConstraintViolation();
		}

		return isValid;
	}
}
