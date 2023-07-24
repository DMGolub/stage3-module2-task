package com.mjc.school.command;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.exception.ValidationException;
import com.mjc.school.utility.AuthorRequestInteractiveReader;

public class AuthorCreateCommand extends Command<AuthorRequestDto, AuthorResponseDto, Long> {

	private static final String SUCCESS_MESSAGE = "Record saved successfully!";
	private static final String ERROR_MESSAGE = "We've got an error: ";

	public AuthorCreateCommand(final BaseController<AuthorRequestDto, AuthorResponseDto, Long> controller) {
		super(controller);
	}

	@Override
	public void execute() {
		try {
			AuthorRequestDto newAuthor = AuthorRequestInteractiveReader.read(null);
			controller.create(newAuthor);
			System.out.println(SUCCESS_MESSAGE);
		} catch (EntityNotFoundException | ValidationException e) {
			System.out.println(ERROR_MESSAGE + e.getMessage());
		}
	}
}