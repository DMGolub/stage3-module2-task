package com.mjc.school.service.aspect;

import com.mjc.school.service.utility.DtoValidator;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidateRequestIdAspect {

	private final DtoValidator dtoValidator;

	public ValidateRequestIdAspect(final DtoValidator dtoValidator) {
		this.dtoValidator = dtoValidator;
	}

	@Before("@annotation(validateRequestId) && args(id)")
	public void validateRequestId(
		final com.mjc.school.service.annotation.ValidateRequestId validateRequestId,
		final Long id
	) {
		dtoValidator.validateId(id, validateRequestId.value());
	}
}