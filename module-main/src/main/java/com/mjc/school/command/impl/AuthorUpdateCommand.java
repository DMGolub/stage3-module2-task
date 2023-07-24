package com.mjc.school.command.impl;

import com.mjc.school.command.Command;
import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.exception.ValidationException;
import com.mjc.school.utility.AuthorRequestInteractiveReader;
import com.mjc.school.utility.ConsoleReader;

import static com.mjc.school.constants.Constants.AUTHOR_UPDATE_ERROR_MESSAGE;
import static com.mjc.school.constants.Constants.AUTHOR_UPDATE_SUCCESS_MESSAGE;
import static com.mjc.school.constants.Constants.AUTHOR_UPDATE_WELCOME_MESSAGE;

public class AuthorUpdateCommand implements Command {

	private final BaseController<AuthorRequestDto, AuthorResponseDto, Long> controller;

	public AuthorUpdateCommand(final BaseController<AuthorRequestDto, AuthorResponseDto, Long> controller) {
		this.controller = controller;
	}

	@Override
	public void execute() {
		try {
			final Long id = ConsoleReader.readPositiveLong(AUTHOR_UPDATE_WELCOME_MESSAGE);
			AuthorRequestDto newAuthor = AuthorRequestInteractiveReader.read(id);
			controller.update(newAuthor);
			System.out.println(AUTHOR_UPDATE_SUCCESS_MESSAGE);
		} catch (EntityNotFoundException | ValidationException e) {
			System.out.println(AUTHOR_UPDATE_ERROR_MESSAGE + e.getMessage());
		}
	}
}