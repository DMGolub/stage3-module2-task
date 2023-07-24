package com.mjc.school.command;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.exception.ValidationException;
import com.mjc.school.utility.AuthorRequestInteractiveReader;
import com.mjc.school.utility.ConsoleReader;

public class AuthorUpdateCommand extends Command<AuthorRequestDto, AuthorResponseDto, Long> {

	private static final String WELCOME_MESSAGE = "Enter author id:";
	private static final String SUCCESS_MESSAGE = "Record updated successfully!";
	private static final String ERROR_MESSAGE = "We've got an error: ";

	public AuthorUpdateCommand(final BaseController<AuthorRequestDto, AuthorResponseDto, Long> controller) {
		super(controller);
	}

	@Override
	public void execute() {
		try {
			final Long id = ConsoleReader.readPositiveLong(WELCOME_MESSAGE);
			AuthorRequestDto newAuthor = AuthorRequestInteractiveReader.read(id);
			controller.update(newAuthor);
			System.out.println(SUCCESS_MESSAGE);
		} catch (EntityNotFoundException | ValidationException e) {
			System.out.println(ERROR_MESSAGE + e.getMessage());
		}
	}
}