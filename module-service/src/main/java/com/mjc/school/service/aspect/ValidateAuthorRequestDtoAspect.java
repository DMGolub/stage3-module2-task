package com.mjc.school.service.aspect;

import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.utility.DtoValidator;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidateAuthorRequestDtoAspect {

	private final DtoValidator dtoValidator;

	public ValidateAuthorRequestDtoAspect(final DtoValidator dtoValidator) {
		this.dtoValidator = dtoValidator;
	}

	@Before("@annotation(com.mjc.school.service.annotation.ValidateAuthorRequestDto) && args(request)")
	public void validateAuthorRequestDto(final AuthorRequestDto request) {
		dtoValidator.validateAuthorRequestDTO(request);
	}
}