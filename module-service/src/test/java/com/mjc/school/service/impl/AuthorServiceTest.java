package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.service.AuthorMapper;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.util.Util;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

	@Mock
	private BaseRepository<Author, Long> authorRepository;
	@Mock
	private AuthorMapper authorMapper;
	@InjectMocks
	private AuthorService authorService;

	@Nested
	class TestCreate {

		@Test
		void create_shouldReturnSavedEntity_whenValidRequestDtoProvided() {
			final long authorId = 1L;
			final String authorName = "Some valid name";
			final AuthorRequestDto request = new AuthorRequestDto(null, authorName);
			final Author mappedAuthor = Util.dtoToAuthor(request);
			when(authorMapper.dtoToModel(request)).thenReturn(mappedAuthor);
			final LocalDateTime date = LocalDateTime.now();
			final Author savedAuthor = new Author(authorId, authorName, date, date);
			when(authorRepository.create(any())).thenReturn(savedAuthor);
			final AuthorResponseDto response = Util.authorToDTO(savedAuthor);
			when(authorMapper.modelToDto(savedAuthor)).thenReturn(response);

			final AuthorResponseDto result = authorService.create(request);

			verify(authorMapper, times(1)).dtoToModel(request);
			verify(authorRepository, times(1)).create(mappedAuthor);
			verify(authorMapper, times(1)).modelToDto(savedAuthor);
			assertEquals(response, result);
		}
	}

	@Nested
	class TestReadById {

		@Test
		void readById_shouldThrowEntityNotFoundException_whenThereIsNoEntityWithGivenId() {
			final long id = 99L;
			when(authorRepository.readById(id)).thenReturn(Optional.empty());

			assertThrows(EntityNotFoundException.class, () -> authorService.readById(id));
			verify(authorRepository, times(1)).readById(any());
			verifyNoInteractions(authorMapper);
		}

		@Test
		void readById_shouldReturnDTO_whenEntityWithGivenIdIsFound() {
			final long id = 2L;
			final Author toBeFound = Util.createTestAuthor(id);
			when(authorRepository.readById(id)).thenReturn(Optional.of(toBeFound));
			final AuthorResponseDto expected = Util.authorToDTO(toBeFound);
			when(authorMapper.modelToDto(toBeFound)).thenReturn(expected);

			assertEquals(expected, authorService.readById(id));
			verify(authorRepository, times(1)).readById(id);
			verify(authorMapper, times(1)).modelToDto(toBeFound);
		}
	}

	@Nested
	class TesReadAll {

		@Test
		void readAll_shouldReturnEmptyDTOList_whenRepositoryReturnsEmptyList() {
			when(authorRepository.readAll()).thenReturn(new ArrayList<>());

			final List<AuthorResponseDto> expected = new ArrayList<>();

			assertEquals(expected, authorService.readAll());
			verify(authorRepository, times(1)).readAll();
			verify(authorMapper, times(1)).modelListToDtoList(new ArrayList<>());
		}

		@Test
		void readAll_shouldReturnTwoDTO_whenRepositoryReturnsTwoEntities() {
			final List<Author> allAuthors = Arrays.asList(
				Util.createTestAuthor(1L),
				Util.createTestAuthor(2L)
			);
			when(authorRepository.readAll()).thenReturn(allAuthors);
			final List<AuthorResponseDto> expected = Util.authorListToAuthorDTOList(allAuthors);
			when(authorMapper.modelListToDtoList(allAuthors)).thenReturn(expected);

			assertEquals(expected, authorService.readAll());
			verify(authorRepository, times(1)).readAll();
			verify(authorMapper, times(1)).modelListToDtoList(allAuthors);
		}
	}

	@Nested
	class TestUpdate {

		@Test
		void update_shouldThrowValidationException_whenIdIsNull() {
			AuthorRequestDto request = Util.createTestAuthorRequest(null);

			assertThrows(EntityNotFoundException.class, () -> authorService.update(request));
		}

		@Test
		void update_shouldThrowEntityNotFoundException_whenEntityWithGivenIdNotFound() {
			final long id = 99L;
			final AuthorRequestDto request = Util.createTestAuthorRequest(id);
			when(authorRepository.existById(request.id())).thenReturn(false);

			assertThrows(EntityNotFoundException.class, () -> authorService.update(request));
			verify(authorRepository, times(1)).existById(request.id());
			verify(authorRepository, times(0)).update(any());
		}

		@Test
		void update_shouldReturnUpdatedEntity_whenValidRequestDtoProvided() {
			final long id = 1L;
			final AuthorRequestDto request = new AuthorRequestDto(id, "Updated name");
			final Author updated = new Author(
				id,
				"Updated name",
				LocalDateTime.of(2023, 7, 17, 16, 30, 0),
				LocalDateTime.now()
			);
			when(authorRepository.existById(request.id())).thenReturn(true);
			when(authorMapper.dtoToModel(request)).thenReturn(updated);
			when(authorRepository.update(any())).thenReturn(updated);
			final AuthorResponseDto response = Util.authorToDTO(updated);
			when(authorMapper.modelToDto(updated)).thenReturn(response);

			final AuthorResponseDto result = authorService.update(request);

			verify(authorRepository, times(1)).existById(request.id());
			verify(authorRepository, times(1)).update(any());
			assertEquals(response, result);
		}
	}

	@Nested
	class TestDeleteById {

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
			final long id = 15L;
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
}