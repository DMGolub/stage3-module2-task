package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.conversation.Operation;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class NewsController implements BaseController<NewsRequestDto, NewsResponseDto, Long> {

	private final BaseService<NewsRequestDto, NewsResponseDto, Long> newsService;

	public NewsController(final BaseService<NewsRequestDto, NewsResponseDto, Long> newsService) {
		this.newsService = newsService;
	}

	@Override
	@CommandHandler(Operation.CREATE_NEWS)
	public NewsResponseDto create(final NewsRequestDto request) {
		return newsService.create(request);
	}

	@Override
	@CommandHandler(Operation.GET_ALL_NEWS)
	public List<NewsResponseDto> readAll() {
		return newsService.readAll();
	}

	@Override
	@CommandHandler(Operation.DELETE_NEWS_BY_ID)
	public NewsResponseDto readById(final Long id) {
		return newsService.readById(id);
	}

	@Override
	@CommandHandler(Operation.UPDATE_NEWS_BY_ID)
	public NewsResponseDto update(final NewsRequestDto request) {
		return newsService.update(request);
	}

	@Override
	@CommandHandler(Operation.DELETE_NEWS_BY_ID)
	public boolean deleteById(final Long id) {
		return newsService.deleteById(id);
	}
}