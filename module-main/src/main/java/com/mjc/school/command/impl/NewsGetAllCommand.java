package com.mjc.school.command.impl;

import com.mjc.school.command.Command;
import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;

import static com.mjc.school.constants.Constants.NEWS_GET_ALL_WELCOME_MESSAGE;

public class NewsGetAllCommand implements Command {

	private final BaseController<NewsRequestDto, NewsResponseDto, Long> controller;

	public NewsGetAllCommand(final BaseController<NewsRequestDto, NewsResponseDto, Long> controller) {
		this.controller = controller;
	}

	@Override
	public void execute() {
		System.out.println(NEWS_GET_ALL_WELCOME_MESSAGE);
		controller.readAll().forEach(System.out::println);
	}
}