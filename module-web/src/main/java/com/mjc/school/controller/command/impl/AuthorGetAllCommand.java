package com.mjc.school.controller.command.impl;

import com.mjc.school.controller.RequestHandler;
import com.mjc.school.controller.command.Command;
import com.mjc.school.service.dto.AuthorResponseDto;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static com.mjc.school.controller.command.Constants.AUTHOR_GET_ALL_WELCOME_MESSAGE;

@Component("4")
public class AuthorGetAllCommand implements Command {

	private final RequestHandler requestHandler;

	public AuthorGetAllCommand(final RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}

	@Override
	public void execute() {
		System.out.println(AUTHOR_GET_ALL_WELCOME_MESSAGE);
		try {
			List<AuthorResponseDto> authors = (List<AuthorResponseDto>) requestHandler.handleRequest(this);
			authors.forEach(a -> System.out.println(a.name() + ", id=" + a.id()));
		} catch (InvocationTargetException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}