package com.mjc.school.controller.command.impl;

import com.mjc.school.controller.RequestHandler;
import com.mjc.school.controller.command.Command;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.exception.ValidationException;
import com.mjc.school.controller.utility.ConsoleReader;
import com.mjc.school.controller.utility.NewsRequestInteractiveReader;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

import static com.mjc.school.controller.command.Constants.NEWS_UPDATE_ERROR_MESSAGE;
import static com.mjc.school.controller.command.Constants.NEWS_UPDATE_SUCCESS_MESSAGE;
import static com.mjc.school.controller.command.Constants.NEWS_UPDATE_WELCOME_MESSAGE;

@Component("7")
public class NewsUpdateCommand implements Command {

	private final RequestHandler requestHandler;

	public NewsUpdateCommand(final RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}

	@Override
	public void execute() {
		try {
			final Long id = ConsoleReader.readPositiveLong(NEWS_UPDATE_WELCOME_MESSAGE);
			NewsRequestDto newNews = NewsRequestInteractiveReader.read(id);
			requestHandler.handleRequest(this, newNews);
			System.out.println(NEWS_UPDATE_SUCCESS_MESSAGE);
		} catch (EntityNotFoundException | ValidationException e) {
			System.out.println(NEWS_UPDATE_ERROR_MESSAGE + e.getMessage());
		} catch (InvocationTargetException e) {
			System.out.println(NEWS_UPDATE_ERROR_MESSAGE + e.getCause().getMessage());
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}