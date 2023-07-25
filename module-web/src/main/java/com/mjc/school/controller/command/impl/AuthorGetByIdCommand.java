package com.mjc.school.controller.command.impl;

import com.mjc.school.controller.RequestHandler;
import com.mjc.school.controller.command.Command;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.controller.utility.ConsoleReader;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

import static com.mjc.school.controller.command.Constants.AUTHOR_CREATE_ERROR_MESSAGE;
import static com.mjc.school.controller.command.Constants.AUTHOR_GET_BY_ID_ERROR_MESSAGE;
import static com.mjc.school.controller.command.Constants.AUTHOR_GET_BY_ID_WELCOME_MESSAGE;

@Component("6")
public class AuthorGetByIdCommand implements Command {

	private final RequestHandler requestHandler;

	public AuthorGetByIdCommand(final RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}

	@Override
	public void execute() {
		final Long id = ConsoleReader.readPositiveLong(AUTHOR_GET_BY_ID_WELCOME_MESSAGE);
		try {
			AuthorResponseDto author = (AuthorResponseDto) requestHandler.handleRequest(this, id);
			System.out.println(author);
		} catch (EntityNotFoundException e) {
			System.out.println(AUTHOR_GET_BY_ID_ERROR_MESSAGE + e.getMessage());
		} catch (InvocationTargetException e) {
			System.out.println(AUTHOR_CREATE_ERROR_MESSAGE + e.getCause().getMessage());
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}