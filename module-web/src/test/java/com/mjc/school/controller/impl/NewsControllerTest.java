package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.impl.NewsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class NewsControllerTest {

	private final BaseService<NewsRequestDto, NewsResponseDto, Long> newsService = Mockito.mock(NewsService.class);
	private final BaseController<NewsRequestDto, NewsResponseDto, Long> newsController = new NewsController(newsService);

	@Test
	void create_shouldInvokeServiceSaveMethod_whenInvoked() {
		final NewsRequestDto request = new NewsRequestDto(null, "Title", "Content", 1L);

		newsController.create(request);
		verify(newsService, times(1)).create(request);
	}

	@Test
	void readAll_shouldInvokeServiceGetAllMethod_whenInvoked() {
		newsController.readAll();
		verify(newsService, times(1)).readAll();
	}

	@Test
	void readById_shouldInvokeServiceGetByIdMethod_whenInvoked() {
		final long id = 1L;

		newsController.readById(id);

		verify(newsService, times(1)).readById(id);
	}

	@Test
	void update_shouldInvokeServiceUpdateMethod_whenInvoked() {
		final NewsRequestDto request = new NewsRequestDto(15L, "Title", "Content", 1L);

		newsController.update(request);
		verify(newsService, times(1)).update(request);
	}

	@Test
	void deleteById_shouldInvokeServiceDelete_whenInvoked() {
		final long id = 1L;

		newsController.deleteById(id);

		verify(newsService, times(1)).deleteById(id);
	}
}