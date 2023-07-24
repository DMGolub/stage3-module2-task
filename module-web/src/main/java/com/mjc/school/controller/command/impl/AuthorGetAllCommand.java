package com.mjc.school.controller.command.impl;

import com.mjc.school.controller.command.Command;
import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.command.Constants;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;

public class AuthorGetAllCommand implements Command {

	private final BaseController<AuthorRequestDto, AuthorResponseDto, Long> controller;

	public AuthorGetAllCommand(BaseController<AuthorRequestDto, AuthorResponseDto, Long> controller) {
		this.controller = controller;
	}

	@Override
	public void execute() {
		System.out.println(Constants.AUTHOR_GET_ALL_WELCOME_MESSAGE);
		controller.readAll().forEach(a -> System.out.println(a.name() + ", id=" + a.id()));
	}
}