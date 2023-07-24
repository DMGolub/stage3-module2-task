package com.mjc.school.controller.command.impl;

import com.mjc.school.controller.command.Command;
import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.command.Constants;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.exception.ValidationException;
import com.mjc.school.controller.utility.ConsoleReader;

public class AuthorDeleteCommand implements Command {

	private final BaseController<AuthorRequestDto, AuthorResponseDto, Long> controller;

	public AuthorDeleteCommand(final BaseController<AuthorRequestDto, AuthorResponseDto, Long> controller) {
		this.controller = controller;
	}

	@Override
	public void execute() {
		final Long id = ConsoleReader.readPositiveLong(Constants.AUTHOR_DELETE_WELCOME_MESSAGE);
		try {
			boolean isDeleted = controller.deleteById(id);
			System.out.println(isDeleted ? Constants.AUTHOR_DELETE_SUCCESS_MESSAGE : Constants.AUTHOR_DELETE_FAIL_MESSAGE);
		} catch (EntityNotFoundException | ValidationException e) {
			System.out.println(Constants.AUTHOR_DELETE_ERROR_MESSAGE + e.getMessage());
		}
	}
}