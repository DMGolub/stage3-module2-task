package com.mjc.school.controller.command.impl;

import com.mjc.school.controller.command.Command;
import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.command.Constants;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.controller.utility.ConsoleReader;

public class NewsGetByIdCommand implements Command {

	private final BaseController<NewsRequestDto, NewsResponseDto, Long> controller;

	public NewsGetByIdCommand(final BaseController<NewsRequestDto, NewsResponseDto, Long> controller) {
		this.controller = controller;
	}

	@Override
	public void execute() {
		final Long id = ConsoleReader.readPositiveLong(Constants.GET_NEWS_BY_ID_WELCOME_MESSAGE);
		try {
			NewsResponseDto news = controller.readById(id);
			System.out.println(news);
		} catch (EntityNotFoundException e) {
			System.out.println(Constants.GET_NEWS_BY_ID_ERROR_MESSAGE + e.getMessage());
		}
	}
}