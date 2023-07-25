package com.mjc.school.service.aspect;

import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.utility.DtoValidator;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidateNewsRequestDtoAspect {

	private final DtoValidator dtoValidator;

	public ValidateNewsRequestDtoAspect(final DtoValidator dtoValidator) {
		this.dtoValidator = dtoValidator;
	}

	@Before("@annotation(com.mjc.school.service.annotation.ValidateNewsRequestDto) && args(request)")
	public void validateNewsRequestDto(NewsRequestDto request) {
		dtoValidator.validateNewsRequestDTO(request);
	}
}