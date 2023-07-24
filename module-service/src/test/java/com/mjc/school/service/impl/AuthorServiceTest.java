package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.AuthorRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.ServiceTestConfiguration;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceTestConfiguration.class)
class AuthorServiceTest {

	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private AuthorService authorService;

	@BeforeEach
	void resetMocks() {
		reset(authorRepository);
	}

	@Nested
	class TestCreate {

		@Test
		void create_shouldThrowValidationException_whenRequestIsNull() {
			assertThrows(ValidationException.class, () -> authorService.create(null));
			verifyNoInteractions(authorRepository);
		}

		@Test
		void create_shouldThrowValidationException_whenNameIsNull() {
			AuthorRequestDto nullName = new AuthorRequestDto(
				null,
				null
			);

			assertThrows(ValidationException.class, () -> authorService.create(nullName));
			verifyNoInteractions(authorRepository);
		}

		@Test
		void create_shouldThrowValidationException_whenNameViolatesLengthConstraints() {
			AuthorRequestDto shortName = new AuthorRequestDto(
				null,
				"N"
			);
			AuthorRequestDto longName = new AuthorRequestDto(
				null,
				"N".repeat(50)
			);

			assertThrows(ValidationException.class, () -> authorService.create(shortName));
			assertThrows(ValidationException.class, () -> authorService.create(longName));
			verifyNoInteractions(authorRepository);
		}

		@Test
		void create_shouldReturnSavedEntity_whenValidRequestDtoProvided() {
			final long authorId = 1L;
			AuthorRequestDto request = new AuthorRequestDto(
				null,
				"Some valid name"
			);
			when(authorRepository.create(any())).then(AdditionalAnswers.returnsFirstArg());

			AuthorResponseDto response = authorService.create(request);

			verify(authorRepository, times(1)).create(any());
			assertNotNull(response);
			assertEquals(request.id(), response.id());
		}
	}

	@Nested
	class TestReadById {

		@Test
		void readById_shouldThrowValidationException_whenIdIsNull() {
			assertThrows(ValidationException.class, () -> authorService.readById(null));
			verifyNoInteractions(authorRepository);
		}

		@Test
		void readById_shouldThrowValidationException_whenIdIsNegative() {
			final long id = -5L;

			assertThrows(ValidationException.class, () -> authorService.readById(id));
			verifyNoInteractions(authorRepository);
		}

		@Test
		void readById_shouldThrowValidationException_whenIdIsZero() {
			final long id = 0;

			assertThrows(ValidationException.class, () -> authorService.readById(id));
			verifyNoInteractions(authorRepository);
		}

		@Test
		void readById_shouldThrowEntityNotFoundException_whenThereIsNoEntityWithGivenId() {
			final long id = 99L;
			when(authorRepository.readById(id)).thenReturn(Optional.empty());

			assertThrows(EntityNotFoundException.class, () -> authorService.readById(id));
			verify(authorRepository, times(1)).readById(any());
		}

		@Test
		void readById_shouldReturnDTO_whenEntityWithGivenIdIsFound() {
			final long id = 2L;
			final AuthorModel toBeFound = createTestAuthor(id);
			when(authorRepository.existById(id)).thenReturn(true);
			when(authorRepository.readById(id)).thenReturn(Optional.of(toBeFound));

			AuthorResponseDto expected = authorToDTO(toBeFound);

			assertEquals(expected, authorService.readById(id));
			verify(authorRepository, times(1)).readById(id);
		}
	}

	@Nested
	class TesReadAll {

		@Test
		void readAll_shouldReturnEmptyDTOList_whenRepositoryReturnsEmptyList() {
			when(authorRepository.readAll()).thenReturn(new ArrayList<>());

			List<AuthorResponseDto> expected = new ArrayList<>();

			assertEquals(expected, authorService.readAll());
			verify(authorRepository, times(1)).readAll();
		}

		@Test
		void readAll_shouldReturnTwoDTO_whenRepositoryReturnsTwoEntities() {
			final List<AuthorModel> allAuthors = Arrays.asList(
				createTestAuthor(1L),
				createTestAuthor(2L)
			);
			when(authorRepository.readAll()).thenReturn(allAuthors);

			List<AuthorResponseDto> expected = authorListToAuthorDTOList(allAuthors);

			assertEquals(expected, authorService.readAll());
			verify(authorRepository, times(1)).readAll();
		}
	}

	@Nested
	class TestUpdate {

		@Test
		void update_shouldThrowValidationException_whenRequestIsNull() {
			assertThrows(ValidationException.class, () -> authorService.update(null));
			verifyNoInteractions(authorRepository);
		}

		@Test
		void update_shouldThrowValidationException_whenIdIsNull() {
			assertThrows(ValidationException.class, () -> authorService.update(null));
			verifyNoInteractions(authorRepository);
		}

		@Test
		void update_shouldThrowValidationException_whenIdIsNegative() {
			final long id = -5L;
			AuthorRequestDto request = createTestRequest(id);

			assertThrows(ValidationException.class, () -> authorService.update(request));
			verifyNoInteractions(authorRepository);
		}

		@Test
		void update_shouldThrowValidationException_whenIdIsZero() {
			final long id = 0;
			AuthorRequestDto request = createTestRequest(id);

			assertThrows(ValidationException.class, () -> authorService.update(request));
			verifyNoInteractions(authorRepository);
		}

		@Test
		void update_shouldThrowEntityNotFoundException_whenEntityWithGivenIdNotFound() {
			final long id = 99L;
			AuthorRequestDto request = createTestRequest(id);
			when(authorRepository.existById(request.id())).thenReturn(false);
			when(authorRepository.update(any())).thenThrow(new NoSuchElementException());

			assertThrows(EntityNotFoundException.class, () -> authorService.update(request));
			verify(authorRepository, times(1)).existById(request.id());
			verify(authorRepository, times(0)).update(any());
		}

		@Test
		void update_shouldThrowValidationException_whenNameIsNull() {
			AuthorRequestDto nullName = new AuthorRequestDto(1L, null);

			assertThrows(ValidationException.class, () -> authorService.update(nullName));
			verifyNoInteractions(authorRepository);
		}

		@Test
		void update_shouldThrowValidationException_whenNameViolatesLengthConstraints() {
			AuthorRequestDto shortName = new AuthorRequestDto(1L, "N");
			AuthorRequestDto longName = new AuthorRequestDto(1L, "N".repeat(50));

			assertThrows(ValidationException.class, () -> authorService.update(shortName));
			assertThrows(ValidationException.class, () -> authorService.update(longName));
			verifyNoInteractions(authorRepository);
		}

		@Test
		void update_shouldReturnUpdatedEntity_whenValidRequestDtoProvided() {
			final long id = 1L;
			AuthorRequestDto request = new AuthorRequestDto(id, "Updated name");
			AuthorModel previous = new AuthorModel(
				id,
				"Some old name",
				LocalDateTime.of(2023, 7, 17, 16, 30, 0),
				LocalDateTime.of(2023, 7, 17, 16, 30, 0)
			);
			AuthorModel updated = new AuthorModel(
				id,
				"Updated name",
				LocalDateTime.of(2023, 7, 17, 16, 30, 0),
				LocalDateTime.now()
			);
			when(authorRepository.existById(request.id())).thenReturn(true);
			when(authorRepository.readById(id)).thenReturn(Optional.of(previous));
			when(authorRepository.update(any())).thenReturn(updated);

			AuthorResponseDto response = authorService.update(request);

			verify(authorRepository, times(1)).existById(request.id());
			verify(authorRepository, times(1)).update(any());
			assertNotNull(response);
			assertEquals(request.name(), response.name());
			assertEquals(previous.getCreateDate(), response.createDate());
		}
	}

	@Nested
	class TestDeleteById {

		@Test
		void deleteById_shouldThrowValidationException_whenIdIsNull() {
			assertThrows(ValidationException.class, () -> authorService.deleteById(null));
			verifyNoInteractions(authorRepository);
		}

		@Test
		void deleteById_shouldThrowValidationException_whenIdIsNegative() {
			final long id = -5L;
			assertThrows(ValidationException.class, () -> authorService.deleteById(id));
			verifyNoInteractions(authorRepository);
		}

		@Test
		void deleteById_shouldThrowValidationException_whenIdIsZero() {
			final long id = 0;
			assertThrows(ValidationException.class, () -> authorService.deleteById(id));
			verifyNoInteractions(authorRepository);
		}

		@Test
		void deleteById_shouldThrowEntityNotFoundException_whenThereIsNoEntityWithGivenId() {
			final long id = 5L;
			when(authorRepository.existById(id)).thenReturn(false);

			assertThrows(EntityNotFoundException.class, () -> authorService.deleteById(id));
			verify(authorRepository, times(1)).existById(id);
			verify(authorRepository, times(0)).deleteById(id);
		}

		@Test
		void deleteById_shouldReturnTrue_whenRepositoryDeletesEntityById() {
			final long id = 5L;
			when(authorRepository.existById(id)).thenReturn(true);
			when(authorRepository.deleteById(id)).thenReturn(true);

			assertTrue(authorService.deleteById(id));
			verify(authorRepository, times(1)).existById(id);
			verify(authorRepository, times(1)).deleteById(id);
		}

		@Test
		void deleteById_shouldReturnFalse_whenRepositoryDoesNotDeletesEntityById() {
			final long id = 99L;
			when(authorRepository.existById(id)).thenReturn(true);
			when(authorRepository.deleteById(id)).thenReturn(false);

			assertFalse(authorService.deleteById(id));
			verify(authorRepository, times(1)).existById(id);
			verify(authorRepository, times(1)).deleteById(id);
		}
	}

	private AuthorRequestDto createTestRequest(Long authorId) {
		return new AuthorRequestDto(authorId, "Author News");
	}

	private AuthorModel createTestAuthor(final long id) {
		return new AuthorModel(
			id,
			"Author Name",
			LocalDateTime.of(2023, 7, 17, 16, 30, 0),
			LocalDateTime.of(2023, 7, 17, 16, 30, 0)
		);
	}

	private AuthorResponseDto authorToDTO(AuthorModel author) {
		return new AuthorResponseDto(
			author.getId(),
			author.getName(),
			author.getCreateDate(),
			author.getLastUpdateDate()
		);
	}

	private List<AuthorResponseDto> authorListToAuthorDTOList(List<AuthorModel> authors) {
		return authors.stream()
			.map(this::authorToDTO)
			.collect(Collectors.toList());
	}
}