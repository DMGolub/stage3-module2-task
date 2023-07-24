package com.mjc.school.command.impl;

import com.mjc.school.command.Command;
import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.exception.ValidationException;
import com.mjc.school.utility.NewsRequestInteractiveReader;

import static com.mjc.school.constants.Constants.NEWS_CREATE_ERROR_MESSAGE;
import static com.mjc.school.constants.Constants.NEWS_CREATE_SUCCESS_MESSAGE;

public class NewsCreateCommand implements Command {

	private final BaseController<NewsRequestDto, NewsResponseDto, Long> controller;

	public NewsCreateCommand(final BaseController<NewsRequestDto, NewsResponseDto, Long> controller) {
		this.controller = controller;
	}

	@Override
	public void execute() {
		try {
			NewsRequestDto newNews = NewsRequestInteractiveReader.read(null);
			controller.create(newNews);
			System.out.println(NEWS_CREATE_SUCCESS_MESSAGE);
		} catch (EntityNotFoundException | ValidationException e) {
			System.out.println(NEWS_CREATE_ERROR_MESSAGE + e.getMessage());
		}
	}
}