package com.mjc.school.command;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.exception.ValidationException;
import com.mjc.school.utility.ConsoleReader;

public class AuthorDeleteCommand extends Command<AuthorRequestDto, AuthorResponseDto, Long> {

	private static final String WELCOME_MESSAGE = "Enter author id:";
	private static final String SUCCESS_MESSAGE = "Author deleted successfully";
	private static final String FAIL_MESSAGE = "Could not delete author";
	private static final String ERROR_MESSAGE = "We've got an error: ";

	public AuthorDeleteCommand(final BaseController<AuthorRequestDto, AuthorResponseDto, Long> controller) {
		super(controller);
	}

	@Override
	public void execute() {
		final Long id = ConsoleReader.readPositiveLong(WELCOME_MESSAGE);
		try {
			boolean isDeleted = controller.deleteById(id);
			System.out.println(isDeleted ? SUCCESS_MESSAGE : FAIL_MESSAGE);
		} catch (EntityNotFoundException | ValidationException e) {
			System.out.println(ERROR_MESSAGE + e.getMessage());
		}
	}
}