package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
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
	public AuthorResponseDto create(final AuthorRequestDto request) {
		return authorService.create(request);
	}

	@Override
	public List<AuthorResponseDto> readAll() {
		return authorService.readAll();
	}

	@Override
	public AuthorResponseDto readById(final Long id) {
		return authorService.readById(id);
	}

	@Override
	public AuthorResponseDto update(final AuthorRequestDto request) {
		return authorService.update(request);
	}

	@Override
	public boolean deleteById(final Long id) {
		return authorService.deleteById(id);
	}
}