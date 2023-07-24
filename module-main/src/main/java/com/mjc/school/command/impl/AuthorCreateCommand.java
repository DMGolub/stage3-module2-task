package com.mjc.school.command.impl;

import com.mjc.school.command.Command;
import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.exception.ValidationException;
import com.mjc.school.utility.AuthorRequestInteractiveReader;

import static com.mjc.school.constants.Constants.AUTHOR_CREATE_ERROR_MESSAGE;
import static com.mjc.school.constants.Constants.AUTHOR_CREATE_SUCCESS_MESSAGE;

public class AuthorCreateCommand implements Command {

	private final BaseController<AuthorRequestDto, AuthorResponseDto, Long> controller;

	public AuthorCreateCommand(final BaseController<AuthorRequestDto, AuthorResponseDto, Long> controller) {
		this.controller = controller;
	}

	@Override
	public void execute() {
		try {
			AuthorRequestDto newAuthor = AuthorRequestInteractiveReader.read(null);
			controller.create(newAuthor);
			System.out.println(AUTHOR_CREATE_SUCCESS_MESSAGE);
		} catch (EntityNotFoundException | ValidationException e) {
			System.out.println(AUTHOR_CREATE_ERROR_MESSAGE + e.getMessage());
		}
	}
}