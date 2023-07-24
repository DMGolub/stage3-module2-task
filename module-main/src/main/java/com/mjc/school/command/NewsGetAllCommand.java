package com.mjc.school.command;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;

public class NewsGetAllCommand extends Command<NewsRequestDto, NewsResponseDto, Long> {

	private static final String WELCOME_MESSAGE = "All available news:";

	public NewsGetAllCommand(final BaseController<NewsRequestDto, NewsResponseDto, Long> controller) {
		super(controller);
	}

	@Override
	public void execute() {
		System.out.println(WELCOME_MESSAGE);
		controller.readAll().forEach(System.out::println);
	}
}