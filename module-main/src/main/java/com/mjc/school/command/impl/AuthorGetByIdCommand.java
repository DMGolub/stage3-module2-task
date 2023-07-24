package com.mjc.school.command.impl;

import com.mjc.school.command.Command;
import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.utility.ConsoleReader;

import static com.mjc.school.constants.Constants.AUTHOR_GET_BY_ID_ERROR_MESSAGE;
import static com.mjc.school.constants.Constants.AUTHOR_GET_BY_ID_WELCOME_MESSAGE;

public class AuthorGetByIdCommand implements Command {

	private final BaseController<AuthorRequestDto, AuthorResponseDto, Long> controller;

	public AuthorGetByIdCommand(final BaseController<AuthorRequestDto, AuthorResponseDto, Long> controller) {
		this.controller = controller;
	}

	@Override
	public void execute() {
		final Long id = ConsoleReader.readPositiveLong(AUTHOR_GET_BY_ID_WELCOME_MESSAGE);
		try {
			AuthorResponseDto news = controller.readById(id);
			System.out.println(news);
		} catch (EntityNotFoundException e) {
			System.out.println(AUTHOR_GET_BY_ID_ERROR_MESSAGE + e.getMessage());
		}
	}
}