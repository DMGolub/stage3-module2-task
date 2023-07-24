package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.conversation.Operation;
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
	@CommandHandler(Operation.CREATE_AUTHOR)
	public AuthorResponseDto create(final AuthorRequestDto request) {
		return authorService.create(request);
	}

	@Override
	@CommandHandler(Operation.GET_ALL_AUTHORS)
	public List<AuthorResponseDto> readAll() {
		return authorService.readAll();
	}

	@Override
	@CommandHandler(Operation.GET_AUTHOR_BY_ID)
	public AuthorResponseDto readById(final Long id) {
		return authorService.readById(id);
	}

	@Override
	@CommandHandler(Operation.UPDATE_AUTHOR_BY_ID)
	public AuthorResponseDto update(final AuthorRequestDto request) {
		return authorService.update(request);
	}

	@Override
	@CommandHandler(Operation.DELETE_AUTHOR_BY_ID)
	public boolean deleteById(final Long id) {
		return authorService.deleteById(id);
	}
}