package com.mjc.school.service.utility;

import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.exception.ServiceErrorCode;
import com.mjc.school.service.exception.ValidationException;
import org.springframework.stereotype.Component;

import static com.mjc.school.service.exception.ServiceErrorCode.NOT_NULL_CONSTRAINT_VIOLATION;
import static com.mjc.school.service.exception.ServiceErrorCode.RANGE_CONSTRAINT_VIOLATION;

@Component
public class DtoValidator {

	private static final String AUTHOR_REQUEST_NAME = "author request";
	private static final String NEWS_REQUEST_NAME = "news request";
	private static final String AUTHOR_NAME = "author name";
	private static final String NEWS_TITLE_NAME = "news title";
	private static final String NEWS_CONTENT_NAME = "news content";
	private static final String AUTHOR_ID = "author id";
	private static final int AUTHOR_TITLE_LENGTH_MIN = 3;
	private static final int AUTHOR_TITLE_LENGTH_MAX = 15;
	private static final int NEWS_TITLE_LENGTH_MIN = 5;
	private static final int NEWS_TITLE_LENGTH_MAX = 30;
	private static final int NEWS_CONTENT_LENGTH_MIN = 5;
	private static final int NEWS_CONTENT_LENGTH_MAX = 255;

	public void validateAuthorRequestDTO(AuthorRequestDto request) {
		validateNotNull(request, AUTHOR_REQUEST_NAME);
		validateStringProperty(
			request.name(),
			AUTHOR_NAME,
			AUTHOR_TITLE_LENGTH_MIN,
			AUTHOR_TITLE_LENGTH_MAX
		);
	}

	public void validateNewsRequestDTO(NewsRequestDto request) {
		validateNotNull(request, NEWS_REQUEST_NAME);
		validateStringProperty(
			request.title(),
			NEWS_TITLE_NAME,
			NEWS_TITLE_LENGTH_MIN,
			NEWS_TITLE_LENGTH_MAX
		);
		validateStringProperty(
			request.content(),
			NEWS_CONTENT_NAME,
			NEWS_CONTENT_LENGTH_MIN,
			NEWS_CONTENT_LENGTH_MAX
		);
		if (request.authorId() != null) {
			validatePositive(request.authorId(), AUTHOR_ID);
		}
	}

	public void validateId(Long id, String name) {
		validateNotNull(id, name);
		validatePositive(id, name);
	}

	private void validateStringProperty(
		final String property,
		final String name,
		final int lengthMin,
		final int lengthMax
	) {
		validateNotNull(property, name);
		validateRange(property.length(), name, lengthMin, lengthMax);
	}

	private void validateRange(
		final int number,
		final String name,
		final int rangeBegin,
		final int rangeEnd
	) {
		if (number < rangeBegin || number > rangeEnd) {
			throw new ValidationException(
				String.format(RANGE_CONSTRAINT_VIOLATION.getMessage(), name + " length", rangeBegin, rangeEnd),
				RANGE_CONSTRAINT_VIOLATION.getCode());
		}
	}

	private void validatePositive(long number, String name) {
		if (number < 1) {
			throw new ValidationException(
				String.format(ServiceErrorCode.POSITIVE_CONSTRAINT_VIOLATION.getMessage(), name, name, number),
				ServiceErrorCode.POSITIVE_CONSTRAINT_VIOLATION.getCode());
		}
	}

	private void validateNotNull(Object obj, String name) {
		if (obj == null) {
			throw new ValidationException(
				String.format(NOT_NULL_CONSTRAINT_VIOLATION.getMessage(), name),
				NOT_NULL_CONSTRAINT_VIOLATION.getCode()
			);
		}
	}
}