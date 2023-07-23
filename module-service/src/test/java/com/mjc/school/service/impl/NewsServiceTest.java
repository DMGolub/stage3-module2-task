package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.impl.AuthorRepository;
import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.exception.ValidationException;
import com.mjc.school.service.utility.DtoValidator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class NewsServiceTest {

	private BaseRepository<AuthorModel, Long> authorRepository = mock(AuthorRepository.class);	// FIXME
	private BaseRepository<NewsModel, Long> newsRepository = mock(NewsRepository.class);		// FIXME
	private DtoValidator dtoValidator = new DtoValidator();										// FIXME
	private BaseService<NewsRequestDto, NewsResponseDto, Long> newsService =
		new NewsService(authorRepository, newsRepository, dtoValidator);

	@Nested
	class TestCreate {

		@Test
		void create_shouldThrowValidationException_whenRequestIsNull() {
			assertThrows(ValidationException.class, () -> newsService.create(null));
			verifyNoInteractions(authorRepository);
			verifyNoInteractions(newsRepository);
		}

		@Test
		void create_shouldThrowValidationException_whenTitleIsNull() {
			NewsRequestDto nullTitle = new NewsRequestDto(
				null,
				null,
				"Some valid content",
				2L
			);

			assertThrows(ValidationException.class, () -> newsService.create(nullTitle));
			verifyNoInteractions(authorRepository);
			verifyNoInteractions(newsRepository);
		}

		@Test
		void create_shouldThrowValidationException_whenTitleViolatesLengthConstraints() {
			NewsRequestDto shortTitle = new NewsRequestDto(
				null,
				"T",
				"Some valid content",
				2L
			);
			NewsRequestDto longTitle = new NewsRequestDto(
				null,
				"T".repeat(50),
				"Some valid content",
				2L
			);

			assertThrows(ValidationException.class, () -> newsService.create(shortTitle));
			assertThrows(ValidationException.class, () -> newsService.create(longTitle));
			verifyNoInteractions(authorRepository);
			verifyNoInteractions(newsRepository);
		}

		@Test
		void create_shouldThrowValidationException_whenContentIsNull() {
			NewsRequestDto nullContent = new NewsRequestDto(
				null,
				"Valid title",
				null,
				2L
			);

			assertThrows(ValidationException.class, () -> newsService.create(nullContent));
			verifyNoInteractions(authorRepository);
			verifyNoInteractions(newsRepository);
		}

		@Test
		void create_shouldThrowValidationException_whenContentViolatesLengthConstraints() {
			NewsRequestDto shortContent = new NewsRequestDto(
				null,
				"Some valid title",
				"C",
				2L
			);
			NewsRequestDto longContent = new NewsRequestDto(
				null,
				"Some valid title",
				"C".repeat(300),
				2L
			);

			assertThrows(ValidationException.class, () -> newsService.create(shortContent));
			assertThrows(ValidationException.class, () -> newsService.create(longContent));
			verifyNoInteractions(authorRepository);
			verifyNoInteractions(newsRepository);
		}

		@Test
		void create_shouldThrowValidationException_whenAuthorIdViolatesConstraints() {
			NewsRequestDto zeroAuthorId = new NewsRequestDto(
				null,
				"Some valid title",
				"Some valid content",
				0L
			);
			NewsRequestDto negativeAuthorId = new NewsRequestDto(
				null,
				"Some valid title",
				"Some valid content",
				-2L
			);

			assertThrows(ValidationException.class, () -> newsService.create(zeroAuthorId));
			assertThrows(ValidationException.class, () -> newsService.create(negativeAuthorId));
			verifyNoInteractions(authorRepository);
			verifyNoInteractions(newsRepository);
		}

		@Test
		void create_shouldThrowEntityNotFoundException_whenAuthorNotFound() {
			final long authorId = 99L;
			NewsRequestDto request = new NewsRequestDto(
				null,
				"Some valid title",
				"Some valid content",
				authorId
			);

			when(authorRepository.existById(authorId)).thenReturn(false);

			assertThrows(EntityNotFoundException.class, () -> newsService.create(request));
			verify(authorRepository, times(1)).existById(authorId);
			verifyNoInteractions(newsRepository);
		}

		@Test
		void create_shouldReturnSavedEntity_whenValidRequestDtoProvided() {
			final long authorId = 1L;
			NewsRequestDto request = new NewsRequestDto(
				null,
				"Some valid title",
				"Some valid content",
				authorId
			);
			when(newsRepository.create(any())).then(AdditionalAnswers.returnsFirstArg());
			when(authorRepository.existById(authorId)).thenReturn(true);

			NewsResponseDto response = newsService.create(request);

			verify(authorRepository, times(1)).existById(authorId);
			verify(newsRepository, times(1)).create(any());
			assertNotNull(response);
			assertEquals(request.title(), response.title());
			assertEquals(request.content(), response.content());
			assertNotNull(response.createDate());
			assertNotNull(response.lastUpdateDate());
			assertEquals(request.authorId(), response.authorId());
		}
	}

	@Nested
	class TestReadById {

		@Test
		void readById_shouldThrowValidationException_whenIdIsNull() {
			assertThrows(ValidationException.class, () -> newsService.readById(null));
			verifyNoInteractions(newsRepository);
		}

		@Test
		void readById_shouldThrowValidationException_whenIdIsNegative() {
			final long id = -5L;

			assertThrows(ValidationException.class, () -> newsService.readById(id));
			verifyNoInteractions(newsRepository);
		}

		@Test
		void readById_shouldThrowValidationException_whenIdIsZero() {
			final long id = 0;

			assertThrows(ValidationException.class, () -> newsService.readById(id));
			verifyNoInteractions(newsRepository);
		}

		@Test
		void readById_shouldThrowEntityNotFoundException_whenThereIsNoEntityWithGivenId() {
			final long id = 99L;
			when(newsRepository.readById(id)).thenReturn(Optional.empty());

			assertThrows(EntityNotFoundException.class, () -> newsService.readById(id));
			verify(newsRepository, times(1)).readById(any());
		}

		@Test
		void readById_shouldReturnDTO_whenEntityWithGivenIdIsFound() {
			final long id = 2L;
			final NewsModel toBeFound = createTestNews(id);
			when(newsRepository.existById(id)).thenReturn(true);
			when(newsRepository.readById(id)).thenReturn(Optional.of(toBeFound));

			NewsResponseDto expected = newsToDTO(toBeFound);

			assertEquals(expected, newsService.readById(id));
			verify(newsRepository, times(1)).readById(id);
		}
	}

	@Nested
	class TestReadAll {

		@Test
		void readAll_shouldReturnEmptyDTOList_whenRepositoryReturnsEmptyList() {
			when(newsRepository.readAll()).thenReturn(new ArrayList<>());

			List<NewsResponseDto> expected = new ArrayList<>();

			assertEquals(expected, newsService.readAll());
			verify(newsRepository, times(1)).readAll();
		}

		@Test
		void readAll_shouldReturnTwoDTOs_whenRepositoryReturnsTwoEntities() {
			final List<NewsModel> allNews = Arrays.asList(
				createTestNews(1L),
				createTestNews(2L)
			);
			when(newsRepository.readAll()).thenReturn(allNews);

			List<NewsResponseDto> expected = newsListToNewsDTOList(allNews);

			assertEquals(expected, newsService.readAll());
			verify(newsRepository, times(1)).readAll();
		}
	}

	@Nested
	class TestUpdate {

		@Test
		void update_shouldThrowValidationException_whenRequestIsNull() {
			assertThrows(ValidationException.class, () -> newsService.update(null));
			verifyNoInteractions(authorRepository);
			verifyNoInteractions(newsRepository);
		}

		@Test
		void update_shouldThrowValidationException_whenIdIsNull() {
			NewsRequestDto request = createTestRequest(null);

			assertThrows(ValidationException.class, () -> newsService.update(request));
			verifyNoInteractions(authorRepository);
			verifyNoInteractions(newsRepository);
		}

		@Test
		void update_shouldThrowValidationException_whenIdIsNegative() {
			final long id = -5L;
			NewsRequestDto request = createTestRequest(id);

			assertThrows(ValidationException.class, () -> newsService.update(request));
			verifyNoInteractions(authorRepository);
			verifyNoInteractions(newsRepository);
		}

		@Test
		void update_shouldThrowValidationException_whenIdIsZero() {
			final long id = 0;
			NewsRequestDto request = createTestRequest(id);

			assertThrows(ValidationException.class, () -> newsService.update(request));
			verifyNoInteractions(authorRepository);
			verifyNoInteractions(newsRepository);
		}

		@Test
		void update_shouldThrowEntityNotFoundException_whenAuthorNotFound() {
			final long id = 5;
			NewsRequestDto request = createTestRequest(id);
			when(authorRepository.existById(request.authorId())).thenReturn(false);

			assertThrows(EntityNotFoundException.class, () -> newsService.update(request));
			verify(authorRepository, times(1)).existById(request.authorId());
			verifyNoInteractions(newsRepository);
		}

		@Test
		void update_shouldThrowEntityNotFoundException_whenEntityWithGivenIdNotFound() {
			final long id = 99L;
			NewsRequestDto request = createTestRequest(id);
			when(authorRepository.existById(request.authorId())).thenReturn(true);
			when(newsRepository.existById(request.id())).thenReturn(false);
			when(newsRepository.update(any())).thenThrow(new NoSuchElementException());

			assertThrows(EntityNotFoundException.class, () -> newsService.update(request));
			verify(authorRepository, times(1)).existById(request.authorId());
			verify(newsRepository, times(1)).existById(request.id());
			verify(newsRepository, times(0)).update(any());
		}

		@Test
		void update_shouldThrowValidationException_whenTitleIsNull() {
			NewsRequestDto nullTitle = new NewsRequestDto(
				1L,
				null,
				"Some valid content",
				2L
			);

			assertThrows(ValidationException.class, () -> newsService.update(nullTitle));
			verifyNoInteractions(authorRepository);
			verifyNoInteractions(newsRepository);
		}

		@Test
		void update_shouldThrowValidationException_whenTitleViolatesLengthConstraints() {
			NewsRequestDto shortTitle = new NewsRequestDto(
				1L,
				"T",
				"Some valid content",
				2L
			);
			NewsRequestDto longTitle = new NewsRequestDto(
				1L,
				"T".repeat(50),
				"Some valid content",
				2L
			);

			assertThrows(ValidationException.class, () -> newsService.update(shortTitle));
			assertThrows(ValidationException.class, () -> newsService.update(longTitle));
			verifyNoInteractions(authorRepository);
			verifyNoInteractions(newsRepository);
		}

		@Test
		void update_shouldThrowValidationException_whenContentIsNull() {
			NewsRequestDto nullContent = new NewsRequestDto(
				1L,
				"Some valid title",
				null,
				2L
			);

			assertThrows(ValidationException.class, () -> newsService.update(nullContent));
			verifyNoInteractions(authorRepository);
			verifyNoInteractions(newsRepository);
		}

		@Test
		void update_shouldThrowValidationException_whenContentViolatesLengthConstraints() {
			NewsRequestDto shortContent = new NewsRequestDto(
				1L,
				"Some valid title",
				"C",
				2L
			);
			NewsRequestDto longContent = new NewsRequestDto(
				1L,
				"Some valid title",
				"C".repeat(300),
				2L
			);

			assertThrows(ValidationException.class, () -> newsService.update(shortContent));
			assertThrows(ValidationException.class, () -> newsService.update(longContent));
			verifyNoInteractions(authorRepository);
			verifyNoInteractions(newsRepository);
		}

		@Test
		void update_shouldThrowValidationException_whenAuthorIdViolatesConstraints() {
			NewsRequestDto nullAuthorId = new NewsRequestDto(
				1L,
				"Some valid title",
				"Some valid content",
				null
			);
			NewsRequestDto negativeAuthorId = new NewsRequestDto(
				1L,
				"Some valid title",
				"Some valid content",
				-2L
			);

			assertThrows(ValidationException.class, () -> newsService.update(nullAuthorId));
			assertThrows(ValidationException.class, () -> newsService.update(negativeAuthorId));
			verifyNoInteractions(authorRepository);
			verifyNoInteractions(newsRepository);
		}

		@Test
		void update_shouldReturnUpdatedEntity_whenValidRequestDtoProvided() {
			final long id = 1L;
			NewsRequestDto request = new NewsRequestDto(
				id,
				"Some updated title",
				"Some updated content",
				2L
			);
			NewsModel previous = new NewsModel(
				id,
				"Some old title",
				"Some old content",
				LocalDateTime.of(2023, 7, 17, 16, 30, 0),
				LocalDateTime.of(2023, 7, 17, 16, 30, 0),
				1L
			);
			NewsModel updated = new NewsModel(
				id,
				"Some updated title",
				"Some updated content",
				LocalDateTime.of(2023, 7, 17, 16, 30, 0),
				LocalDateTime.now(),
				2L
			);
			when(authorRepository.existById(request.authorId())).thenReturn(true);
			when(newsRepository.existById(request.id())).thenReturn(true);
			when(newsRepository.readById(id)).thenReturn(Optional.of(previous));
			when(newsRepository.update(any())).thenReturn(updated);

			NewsResponseDto response = newsService.update(request);

			verify(authorRepository, times(1)).existById(request.authorId());
			verify(newsRepository, times(1)).existById(request.id());
			verify(newsRepository, times(1)).update(any());
			assertNotNull(response);
			assertEquals(request.title(), response.title());
			assertEquals(request.content(), response.content());
			assertEquals(previous.getCreateDate(), response.createDate());
			assertEquals(request.authorId(), response.authorId());
		}
	}

	@Nested
	class TestDeleteById {

		@Test
		void deleteById_shouldThrowValidationException_whenIdIsNull() {
			assertThrows(ValidationException.class, () -> newsService.deleteById(null));
			verifyNoInteractions(newsRepository);
		}

		@Test
		void deleteById_shouldThrowValidationException_whenIdIsNegative() {
			final long id = -5L;
			assertThrows(ValidationException.class, () -> newsService.deleteById(id));
			verifyNoInteractions(newsRepository);
		}

		@Test
		void deleteById_shouldThrowValidationException_whenIdIsZero() {
			final long id = 0;
			assertThrows(ValidationException.class, () -> newsService.deleteById(id));
			verifyNoInteractions(newsRepository);
		}

		@Test
		void deleteById_shouldThrowEntityNotFoundException_whenThereIsNoEntityWithGivenId() {
			final long id = 5L;
			when(newsRepository.existById(id)).thenReturn(false);

			assertThrows(EntityNotFoundException.class, () -> newsService.deleteById(id));
			verify(newsRepository, times(1)).existById(id);
			verify(newsRepository, times(0)).deleteById(id);
		}

		@Test
		void deleteById_shouldReturnTrue_whenRepositoryDeletesEntityById() {
			final long id = 5L;
			when(newsRepository.existById(id)).thenReturn(true);
			when(newsRepository.deleteById(id)).thenReturn(true);

			assertTrue(newsService.deleteById(id));
			verify(newsRepository, times(1)).existById(id);
			verify(newsRepository, times(1)).deleteById(id);
		}

		@Test
		void deleteById_shouldReturnFalse_whenRepositoryDoesNotDeletesEntityById() {
			final long id = 99L;
			when(newsRepository.existById(id)).thenReturn(true);
			when(newsRepository.deleteById(id)).thenReturn(false);

			assertFalse(newsService.deleteById(id));
			verify(newsRepository, times(1)).existById(id);
			verify(newsRepository, times(1)).deleteById(id);
		}
	}

	private NewsModel createTestNews(Long newsId) {
		return new NewsModel(
			newsId,
			"Title",
			"Content",
			LocalDateTime.of(2023, 7, 17, 16, 30, 0),
			LocalDateTime.of(2023, 7, 17, 16, 30, 0),
			1L
		);
	}

	private NewsRequestDto createTestRequest(Long newsId) {
		return new NewsRequestDto(newsId, "Title", "Content", 1L);
	}

	private NewsResponseDto newsToDTO(NewsModel news) {
		return new NewsResponseDto(
			news.getId(),
			news.getTitle(),
			news.getContent(),
			news.getCreateDate(),
			news.getLastUpdateDate(),
			news.getAuthorId()
		);
	}

	private List<NewsResponseDto> newsListToNewsDTOList(List<NewsModel> news) {
		return news.stream()
			.map(this::newsToDTO)
			.collect(Collectors.toList());
	}
}