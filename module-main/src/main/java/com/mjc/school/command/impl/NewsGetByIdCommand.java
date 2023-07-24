package com.mjc.school.command.impl;

import com.mjc.school.command.Command;
import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.utility.ConsoleReader;

import static com.mjc.school.constants.Constants.GET_NEWS_BY_ID_ERROR_MESSAGE;
import static com.mjc.school.constants.Constants.GET_NEWS_BY_ID_WELCOME_MESSAGE;

public class NewsGetByIdCommand implements Command{

	private final BaseController<NewsRequestDto, NewsResponseDto, Long> controller;

	public NewsGetByIdCommand(final BaseController<NewsRequestDto, NewsResponseDto, Long> controller) {
		this.controller = controller;
	}

	@Override
	public void execute() {
		final Long id = ConsoleReader.readPositiveLong(GET_NEWS_BY_ID_WELCOME_MESSAGE);
		try {
			NewsResponseDto news = controller.readById(id);
			System.out.println(news);
		} catch (EntityNotFoundException e) {
			System.out.println(GET_NEWS_BY_ID_ERROR_MESSAGE + e.getMessage());
		}
	}
}