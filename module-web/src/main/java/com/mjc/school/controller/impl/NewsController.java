package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandBody;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.annotation.CommandParam;
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
	@CommandHandler("1")
	public NewsResponseDto create(@CommandBody final NewsRequestDto request) {
		return newsService.create(request);
	}

	@Override
	@CommandHandler("3")
	public List<NewsResponseDto> readAll() {
		return newsService.readAll();
	}

	@Override
	@CommandHandler("5")
	public NewsResponseDto readById(@CommandParam final Long id) {
		return newsService.readById(id);
	}

	@Override
	@CommandHandler("7")
	public NewsResponseDto update(@CommandBody final NewsRequestDto request) {
		return newsService.update(request);
	}

	@Override
	@CommandHandler("9")
	public boolean deleteById(@CommandParam final Long id) {
		return newsService.deleteById(id);
	}
}