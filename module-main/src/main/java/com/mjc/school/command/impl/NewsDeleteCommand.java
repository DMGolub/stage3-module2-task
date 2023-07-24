package com.mjc.school.command.impl;

import com.mjc.school.command.Command;
import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.exception.ValidationException;
import com.mjc.school.utility.ConsoleReader;

import static com.mjc.school.constants.Constants.NEWS_DELETE_ERROR_MESSAGE;
import static com.mjc.school.constants.Constants.NEWS_DELETE_FAIL_MESSAGE;
import static com.mjc.school.constants.Constants.NEWS_DELETE_SUCCESS_MESSAGE;
import static com.mjc.school.constants.Constants.NEWS_DELETE_WELCOME_MESSAGE;

public class NewsDeleteCommand implements Command {

	private final BaseController<NewsRequestDto, NewsResponseDto, Long> controller;

	public NewsDeleteCommand(final BaseController<NewsRequestDto, NewsResponseDto, Long> controller) {
		this.controller = controller;
	}

	@Override
	public void execute() {
		final Long id = ConsoleReader.readPositiveLong(NEWS_DELETE_WELCOME_MESSAGE);
		try {
			boolean isDeleted = controller.deleteById(id);
			System.out.println(isDeleted ? NEWS_DELETE_SUCCESS_MESSAGE : NEWS_DELETE_FAIL_MESSAGE);
		} catch (EntityNotFoundException | ValidationException e) {
			System.out.println(NEWS_DELETE_ERROR_MESSAGE + e.getMessage());
		}
	}
}