package com.mjc.school.controller.command.impl;

import com.mjc.school.controller.command.Command;
import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.command.Constants;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.exception.ValidationException;
import com.mjc.school.controller.utility.ConsoleReader;

public class NewsDeleteCommand implements Command {

	private final BaseController<NewsRequestDto, NewsResponseDto, Long> controller;

	public NewsDeleteCommand(final BaseController<NewsRequestDto, NewsResponseDto, Long> controller) {
		this.controller = controller;
	}

	@Override
	public void execute() {
		final Long id = ConsoleReader.readPositiveLong(Constants.NEWS_DELETE_WELCOME_MESSAGE);
		try {
			boolean isDeleted = controller.deleteById(id);
			System.out.println(isDeleted ? Constants.NEWS_DELETE_SUCCESS_MESSAGE : Constants.NEWS_DELETE_FAIL_MESSAGE);
		} catch (EntityNotFoundException | ValidationException e) {
			System.out.println(Constants.NEWS_DELETE_ERROR_MESSAGE + e.getMessage());
		}
	}
}