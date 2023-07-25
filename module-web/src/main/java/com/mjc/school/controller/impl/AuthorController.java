package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandBody;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.annotation.CommandParam;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthorController implements BaseController<AuthorRequestDto, AuthorResponseDto, Long> {

	private final BaseService<AuthorRequestDto, AuthorResponseDto, Long> authorService;

	public AuthorController(final BaseService<AuthorRequestDto, AuthorResponseDto, Long> authorService) {
		this.authorService = authorService;
	}

	@Override
	@CommandHandler("2")
	public AuthorResponseDto create(@CommandBody final AuthorRequestDto request) {
		return authorService.create(request);
	}

	@Override
	@CommandHandler("4")
	public List<AuthorResponseDto> readAll() {
		return authorService.readAll();
	}

	@Override
	@CommandHandler("6")
	public AuthorResponseDto readById(@CommandParam final Long id) {
		return authorService.readById(id);
	}

	@Override
	@CommandHandler("8")
	public AuthorResponseDto update(@CommandBody final AuthorRequestDto request) {
		return authorService.update(request);
	}

	@Override
	@CommandHandler("10")
	public boolean deleteById(@CommandParam final Long id) {
		return authorService.deleteById(id);
	}
}