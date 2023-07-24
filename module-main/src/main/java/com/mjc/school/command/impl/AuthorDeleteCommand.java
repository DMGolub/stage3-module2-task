package com.mjc.school.command.impl;

import com.mjc.school.command.Command;
import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.exception.ValidationException;
import com.mjc.school.utility.ConsoleReader;

import static com.mjc.school.constants.Constants.AUTHOR_DELETE_ERROR_MESSAGE;
import static com.mjc.school.constants.Constants.AUTHOR_DELETE_FAIL_MESSAGE;
import static com.mjc.school.constants.Constants.AUTHOR_DELETE_SUCCESS_MESSAGE;
import static com.mjc.school.constants.Constants.AUTHOR_DELETE_WELCOME_MESSAGE;

public class AuthorDeleteCommand implements Command {

	private final BaseController<AuthorRequestDto, AuthorResponseDto, Long> controller;

	public AuthorDeleteCommand(final BaseController<AuthorRequestDto, AuthorResponseDto, Long> controller) {
		this.controller = controller;
	}

	@Override
	public void execute() {
		final Long id = ConsoleReader.readPositiveLong(AUTHOR_DELETE_WELCOME_MESSAGE);
		try {
			boolean isDeleted = controller.deleteById(id);
			System.out.println(isDeleted ? AUTHOR_DELETE_SUCCESS_MESSAGE : AUTHOR_DELETE_FAIL_MESSAGE);
		} catch (EntityNotFoundException | ValidationException e) {
			System.out.println(AUTHOR_DELETE_ERROR_MESSAGE + e.getMessage());
		}
	}
}