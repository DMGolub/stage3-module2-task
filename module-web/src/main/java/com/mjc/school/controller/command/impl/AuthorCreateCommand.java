package com.mjc.school.controller.command.impl;

import com.mjc.school.controller.RequestHandler;
import com.mjc.school.controller.command.Command;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.exception.ValidationException;
import com.mjc.school.controller.utility.AuthorRequestInteractiveReader;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

import static com.mjc.school.controller.command.Constants.AUTHOR_CREATE_ERROR_MESSAGE;
import static com.mjc.school.controller.command.Constants.AUTHOR_CREATE_SUCCESS_MESSAGE;

@Component("2")
public class AuthorCreateCommand implements Command {

	private final RequestHandler requestHandler;

	public AuthorCreateCommand(final RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}

	@Override
	public void execute() {
		try {
			AuthorRequestDto newAuthor = AuthorRequestInteractiveReader.read(null);
			requestHandler.handleRequest(this, newAuthor);
			System.out.println(AUTHOR_CREATE_SUCCESS_MESSAGE);
		} catch (EntityNotFoundException | ValidationException e) {
			System.out.println(AUTHOR_CREATE_ERROR_MESSAGE + e.getMessage());
		} catch (InvocationTargetException e) {
			System.out.println(AUTHOR_CREATE_ERROR_MESSAGE + e.getCause().getMessage());
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}