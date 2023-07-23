package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
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
	public NewsResponseDto create(final NewsRequestDto request) {
		return newsService.create(request);
	}

	@Override
	public List<NewsResponseDto> readAll() {
		return newsService.readAll();
	}

	@Override
	public NewsResponseDto readById(final Long id) {
		return newsService.readById(id);
	}

	@Override
	public NewsResponseDto update(final NewsRequestDto request) {
		return newsService.update(request);
	}

	@Override
	public boolean deleteById(final Long id) {
		return newsService.deleteById(id);
	}
}