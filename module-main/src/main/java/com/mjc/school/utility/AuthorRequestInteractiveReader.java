package com.mjc.school.utility;

import com.mjc.school.service.dto.AuthorRequestDto;

public class AuthorRequestInteractiveReader {

	private static final String NAME_MESSAGE = "Enter author name:";
	private static final int AUTHOR_NAME_MIN_LENGTH = 3;
	private static final int AUTHOR_NAME_MAX_LENGTH = 15;

	private AuthorRequestInteractiveReader() {
		// Empty. Hides default public constructor
	}

	public static AuthorRequestDto read(Long id) {
		String name = ConsoleReader.readText(
			NAME_MESSAGE,
			AUTHOR_NAME_MIN_LENGTH,
			AUTHOR_NAME_MAX_LENGTH
		);
		return new AuthorRequestDto(id, name);
	}
}