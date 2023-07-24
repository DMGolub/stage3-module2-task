package com.mjc.school.command.impl;

import com.mjc.school.command.Command;
import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.exception.ValidationException;
import com.mjc.school.utility.ConsoleReader;
import com.mjc.school.utility.NewsRequestInteractiveReader;

import static com.mjc.school.constants.Constants.NEWS_UPDATE_ERROR_MESSAGE;
import static com.mjc.school.constants.Constants.NEWS_UPDATE_SUCCESS_MESSAGE;
import static com.mjc.school.constants.Constants.NEWS_UPDATE_WELCOME_MESSAGE;

public class NewsUpdateCommand implements Command {

	private final BaseController<NewsRequestDto, NewsResponseDto, Long> controller;

	public NewsUpdateCommand(BaseController<NewsRequestDto, NewsResponseDto, Long> controller) {
		this.controller = controller;
	}

	@Override
	public void execute() {
		try {
			final Long id = ConsoleReader.readPositiveLong(NEWS_UPDATE_WELCOME_MESSAGE);
			NewsRequestDto newNews = NewsRequestInteractiveReader.read(id);
			controller.update(newNews);
			System.out.println(NEWS_UPDATE_SUCCESS_MESSAGE);
		} catch (EntityNotFoundException | ValidationException e) {
			System.out.println(NEWS_UPDATE_ERROR_MESSAGE + e.getMessage());
		}
	}
}