package com.mjc.school.command;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;

public class AuthorGetAllCommand extends Command<AuthorRequestDto, AuthorResponseDto, Long> {

	private static final String WELCOME_MESSAGE = "All available authors:";

	public AuthorGetAllCommand(BaseController<AuthorRequestDto, AuthorResponseDto, Long> controller) {
		super(controller);
	}

	@Override
	public void execute() {
		System.out.println(WELCOME_MESSAGE);
		controller.readAll().forEach(a -> System.out.println(a.name() + ", id=" + a.id()));
	}
}