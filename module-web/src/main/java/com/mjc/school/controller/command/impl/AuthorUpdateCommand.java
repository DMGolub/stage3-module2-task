package com.mjc.school.controller.command.impl;

import com.mjc.school.controller.RequestHandler;
import com.mjc.school.controller.command.Command;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.exception.ValidationException;
import com.mjc.school.controller.utility.AuthorRequestInteractiveReader;
import com.mjc.school.controller.utility.ConsoleReader;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

import static com.mjc.school.controller.command.Constants.AUTHOR_CREATE_ERROR_MESSAGE;
import static com.mjc.school.controller.command.Constants.AUTHOR_UPDATE_ERROR_MESSAGE;
import static com.mjc.school.controller.command.Constants.AUTHOR_UPDATE_SUCCESS_MESSAGE;
import static com.mjc.school.controller.command.Constants.AUTHOR_UPDATE_WELCOME_MESSAGE;

@Component("8")
public class AuthorUpdateCommand implements Command {

	private final RequestHandler requestHandler;

	public AuthorUpdateCommand(final RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}

	@Override
	public void execute() {
		try {
			final Long id = ConsoleReader.readPositiveLong(AUTHOR_UPDATE_WELCOME_MESSAGE);
			AuthorRequestDto newAuthor = AuthorRequestInteractiveReader.read(id);
			requestHandler.handleRequest(this, newAuthor);
			System.out.println(AUTHOR_UPDATE_SUCCESS_MESSAGE);
		} catch (EntityNotFoundException | ValidationException e) {
			System.out.println(AUTHOR_UPDATE_ERROR_MESSAGE + e.getMessage());
		} catch (InvocationTargetException e) {
			System.out.println(AUTHOR_CREATE_ERROR_MESSAGE + e.getCause().getMessage());
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}