package com.mjc.school.controller.command.impl;

import com.mjc.school.controller.command.Command;
import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.command.Constants;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.exception.ValidationException;
import com.mjc.school.controller.utility.AuthorRequestInteractiveReader;
import com.mjc.school.controller.utility.ConsoleReader;

public class AuthorUpdateCommand implements Command {

	private final BaseController<AuthorRequestDto, AuthorResponseDto, Long> controller;

	public AuthorUpdateCommand(final BaseController<AuthorRequestDto, AuthorResponseDto, Long> controller) {
		this.controller = controller;
	}

	@Override
	public void execute() {
		try {
			final Long id = ConsoleReader.readPositiveLong(Constants.AUTHOR_UPDATE_WELCOME_MESSAGE);
			AuthorRequestDto newAuthor = AuthorRequestInteractiveReader.read(id);
			controller.update(newAuthor);
			System.out.println(Constants.AUTHOR_UPDATE_SUCCESS_MESSAGE);
		} catch (EntityNotFoundException | ValidationException e) {
			System.out.println(Constants.AUTHOR_UPDATE_ERROR_MESSAGE + e.getMessage());
		}
	}
}