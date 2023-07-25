package com.mjc.school.controller.command.impl;

import com.mjc.school.controller.RequestHandler;
import com.mjc.school.controller.command.Command;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.exception.ValidationException;
import com.mjc.school.controller.utility.NewsRequestInteractiveReader;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

import static com.mjc.school.controller.command.Constants.AUTHOR_CREATE_ERROR_MESSAGE;
import static com.mjc.school.controller.command.Constants.NEWS_CREATE_ERROR_MESSAGE;
import static com.mjc.school.controller.command.Constants.NEWS_CREATE_SUCCESS_MESSAGE;

@Component("1")
public class NewsCreateCommand implements Command {

	private final RequestHandler requestHandler;

	public NewsCreateCommand(final RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}

	@Override
	public void execute() {
		try {
			NewsRequestDto newNews = NewsRequestInteractiveReader.read(null);
			requestHandler.handleRequest(this, newNews);
			System.out.println(NEWS_CREATE_SUCCESS_MESSAGE);
		} catch (EntityNotFoundException | ValidationException e) {
			System.out.println(NEWS_CREATE_ERROR_MESSAGE + e.getMessage());
		} catch (InvocationTargetException e) {
			System.out.println(AUTHOR_CREATE_ERROR_MESSAGE + e.getCause().getMessage());
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}