package com.mjc.school.controller.command.impl;

import com.mjc.school.controller.RequestHandler;
import com.mjc.school.controller.command.Command;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.exception.ValidationException;
import com.mjc.school.controller.utility.ConsoleReader;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

import static com.mjc.school.controller.command.Constants.AUTHOR_CREATE_ERROR_MESSAGE;
import static com.mjc.school.controller.command.Constants.NEWS_DELETE_ERROR_MESSAGE;
import static com.mjc.school.controller.command.Constants.NEWS_DELETE_FAIL_MESSAGE;
import static com.mjc.school.controller.command.Constants.NEWS_DELETE_SUCCESS_MESSAGE;
import static com.mjc.school.controller.command.Constants.NEWS_DELETE_WELCOME_MESSAGE;

@Component("9")
public class NewsDeleteCommand implements Command {

	private final RequestHandler requestHandler;

	public NewsDeleteCommand(final RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}

	@Override
	public void execute() {
		final Long id = ConsoleReader.readPositiveLong(NEWS_DELETE_WELCOME_MESSAGE);
		try {
			boolean isDeleted = (Boolean) requestHandler.handleRequest(this, id);
			System.out.println(isDeleted ? NEWS_DELETE_SUCCESS_MESSAGE : NEWS_DELETE_FAIL_MESSAGE);
		} catch (EntityNotFoundException | ValidationException e) {
			System.out.println(NEWS_DELETE_ERROR_MESSAGE + e.getMessage());
		} catch (InvocationTargetException e) {
			System.out.println(AUTHOR_CREATE_ERROR_MESSAGE + e.getCause().getMessage());
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}