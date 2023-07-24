package com.mjc.school.command;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.utility.ConsoleReader;

public class NewsGetByIdCommand extends Command<NewsRequestDto, NewsResponseDto, Long> {

	private static final String WELCOME_MESSAGE = "Enter news id:";
	private static final String ERROR_MESSAGE = "We've got an error: ";

	public NewsGetByIdCommand(final BaseController<NewsRequestDto, NewsResponseDto, Long> controller) {
		super(controller);
	}

	@Override
	public void execute() {
		final Long id = ConsoleReader.readPositiveLong(WELCOME_MESSAGE);
		try {
			NewsResponseDto news = controller.readById(id);
			System.out.println(news);
		} catch (EntityNotFoundException e) {
			System.out.println(ERROR_MESSAGE + e.getMessage());
		}
	}
}