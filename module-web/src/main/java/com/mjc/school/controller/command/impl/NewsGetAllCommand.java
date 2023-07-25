package com.mjc.school.controller.command.impl;

import com.mjc.school.controller.RequestHandler;
import com.mjc.school.controller.command.Command;
import com.mjc.school.service.dto.NewsResponseDto;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static com.mjc.school.controller.command.Constants.NEWS_GET_ALL_WELCOME_MESSAGE;

@Component("3")
public class NewsGetAllCommand implements Command {

	private final RequestHandler requestHandler;

	public NewsGetAllCommand(final RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}

	@Override
	public void execute() {
		System.out.println(NEWS_GET_ALL_WELCOME_MESSAGE);
		try {
			List<NewsResponseDto> news = (List<NewsResponseDto>) requestHandler.handleRequest(this);
			news.forEach(System.out::println);
		} catch (InvocationTargetException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}