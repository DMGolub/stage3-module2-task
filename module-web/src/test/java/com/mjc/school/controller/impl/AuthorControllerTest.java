package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.impl.AuthorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class AuthorControllerTest {

	private final BaseService<AuthorRequestDto, AuthorResponseDto, Long> authorService = Mockito.mock(AuthorService.class);
	private final BaseController<AuthorRequestDto, AuthorResponseDto, Long> authorController =
		new AuthorController(authorService);

	@Test
	void create_shouldInvokeServiceSaveMethod_whenInvoked() {
		final AuthorRequestDto request = new AuthorRequestDto(null, "Author Name");

		authorController.create(request);
		verify(authorService, times(1)).create(request);
	}

	@Test
	void readAll_shouldInvokeServiceGetAllMethod_whenInvoked() {
		authorController.readAll();
		verify(authorService, times(1)).readAll();
	}

	@Test
	void readById_shouldInvokeServiceGetByIdMethod_whenInvoked() {
		final long id = 1L;

		authorController.readById(id);

		verify(authorService, times(1)).readById(id);
	}

	@Test
	void update_shouldInvokeServiceUpdateMethod_whenInvoked() {
		final AuthorRequestDto request = new AuthorRequestDto(15L, "Author Name");

		authorController.update(request);
		verify(authorService, times(1)).update(request);
	}

	@Test
	void deleteById_shouldInvokeServiceDelete_whenInvoked() {
		final long id = 1L;

		authorController.deleteById(id);

		verify(authorService, times(1)).deleteById(id);
	}
}