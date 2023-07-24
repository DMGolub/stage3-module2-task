package com.mjc.school.command;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.utility.ConsoleReader;

public class AuthorGetByIdCommand extends Command<AuthorRequestDto, AuthorResponseDto, Long> {

	private static final String WELCOME_MESSAGE = "Enter author id:";
	private static final String ERROR_MESSAGE = "We've got an error: ";

	public AuthorGetByIdCommand(final BaseController<AuthorRequestDto, AuthorResponseDto, Long> controller) {
		super(controller);
	}

	@Override
	public void execute() {
		final Long id = ConsoleReader.readPositiveLong(WELCOME_MESSAGE);
		try {
			AuthorResponseDto news = controller.readById(id);
			System.out.println(news);
		} catch (EntityNotFoundException e) {
			System.out.println(ERROR_MESSAGE + e.getMessage());
		}
	}
}