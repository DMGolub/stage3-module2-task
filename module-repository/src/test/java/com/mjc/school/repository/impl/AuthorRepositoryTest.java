package com.mjc.school.repository.impl;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.utility.DataSource;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorRepositoryTest {

	@Mock
	private DataSource dataSource;
	@InjectMocks
	private AuthorRepository repository;

	@Nested
	class TestReadAll {

		@Test
		void readAll_shouldReturnEmptyList_whenStorageIsEmpty() {
			when(dataSource.getAuthors()).thenReturn(Collections.emptyList());

			assertEquals(Collections.emptyList(), repository.readAll());
			verify(dataSource, times(1)).getAuthors();
		}

		@Test
		void readAll_shouldReturnTwoEntities_whenThereAreTwoEntitiesInTheStorage() {
			final List<AuthorModel> storage = Arrays.asList(
				createTestAuthor(1L),
				createTestAuthor(2L)
			);
			when(dataSource.getAuthors()).thenReturn(storage);

			assertEquals(storage, repository.readAll());
			verify(dataSource, times(1)).getAuthors();
		}
	}

	@Nested
	class TestReadById {

		@Test
		void readById_shouldReturnEmptyOptional_whenThereIsNoEntityWithGivenId() {
			final List<AuthorModel> storage = Arrays.asList(
				createTestAuthor(1L),
				createTestAuthor(2L)
			);
			when(dataSource.getAuthors()).thenReturn(storage);

			assertEquals(Optional.empty(), repository.readById(3L));
			verify(dataSource, times(1)).getAuthors();
		}

		@Test
		void readById_shouldReturnEntity_whenEntityWithGivenIdExists() {
			final long id = 2L;
			final AuthorModel expected = createTestAuthor(id);
			final List<AuthorModel> storage = Arrays.asList(createTestAuthor(1L), expected);
			when(dataSource.getAuthors()).thenReturn(storage);

			assertEquals(Optional.of(expected), repository.readById(id));
			verify(dataSource, times(1)).getAuthors();
		}
	}

	@Nested
	class TestCreate {

		@Test
		void create_shouldSaveEntityAndReturnEntityWithId1_whenStorageIsEmpty() {
			final List<AuthorModel> storage = new ArrayList<>();
			when(dataSource.getAuthors()).thenReturn(storage);

			AuthorModel author = createTestAuthor(null);
			AuthorModel expected = createTestAuthor(1L);

			assertEquals(expected, repository.create(author));
			verify(dataSource, times(1)).getAuthors();
		}

		@Test
		void create_shouldSaveEntityAndReturnEntityWithId3_whenStorageContainsTwoEntities() {
			final List<AuthorModel> storage = new ArrayList<>();
			storage.add(createTestAuthor(1L));
			storage.add(createTestAuthor(2L));
			when(dataSource.getAuthors()).thenReturn(storage);

			AuthorModel author = createTestAuthor(null);
			AuthorModel expected = createTestAuthor(3L);

			assertEquals(expected, repository.create(author));
			verify(dataSource, times(1)).getAuthors();
		}

		@Test
		void create_shouldSaveEntityAndReturnEntityWithId4_whenStorageContainsTwoEntitiesWithId12And15() {
			final List<AuthorModel> storage = new ArrayList<>();
			storage.add(createTestAuthor(12L));
			storage.add(createTestAuthor(15L));
			when(dataSource.getAuthors()).thenReturn(storage);

			AuthorModel author = createTestAuthor(null);
			AuthorModel expected = createTestAuthor(16L);

			assertEquals(expected, repository.create(author));
			verify(dataSource, times(1)).getAuthors();
		}
	}

	@Nested
	class TestUpdate {

		@Test
		void update_shouldThrowNoSuchElementException_whenThereIsNoEntityWithGivenId() {
			final List<AuthorModel> storage = new ArrayList<>();
			storage.add(createTestAuthor(1L));
			storage.add(createTestAuthor(2L));
			when(dataSource.getAuthors()).thenReturn(storage);

			AuthorModel updated = createTestAuthor(99L);

			assertThrows(NoSuchElementException.class, () -> repository.update(updated));
			verify(dataSource, times(1)).getAuthors();
		}

		@Test
		void update_shouldReturnUpdatedEntity_whenEntityIsValid() {
			final List<AuthorModel> storage = new ArrayList<>();
			storage.add(createTestAuthor(1L));
			storage.add(createTestAuthor(2L));
			when(dataSource.getAuthors()).thenReturn(storage);

			final AuthorModel updated = createTestAuthor(2L);
			updated.setName("Updated name");
			List<AuthorModel> expected = Arrays.asList(createTestAuthor(1L), updated);

			assertEquals(updated, repository.update(updated));
			assertEquals(expected, storage);
			verify(dataSource, times(1)).getAuthors();
		}
	}

	@Nested
	class TestDeleteById {

		@Test
		void delete_shouldReturnFalse_whenStorageIsEmpty() {
			final long id = 99L;
			when(dataSource.getAuthors()).thenReturn(Collections.emptyList());

			assertFalse(repository.deleteById(id));
			verify(dataSource, times(1)).getAuthors();
		}

		@Test
		void delete_shouldReturnFalse_whenThereIsNoEntityWithGivenId() {
			final long id = 99L;
			final List<AuthorModel> storage = Arrays.asList(
				createTestAuthor(1L),
				createTestAuthor(2L)
			);
			when(dataSource.getAuthors()).thenReturn(storage);

			assertFalse(repository.deleteById(id));
			verify(dataSource, times(1)).getAuthors();
		}

		@Test
		void delete_shouldReturnTrue_whenEntityWithGivenIdDeleted() {
			final long id = 3L;
			final List<AuthorModel> storage = new ArrayList<>();
			storage.add(createTestAuthor(1L));
			storage.add(createTestAuthor(2L));
			storage.add(createTestAuthor(id));
			storage.add(createTestAuthor(4L));
			storage.add(createTestAuthor(5L));
			when(dataSource.getAuthors()).thenReturn(storage);

			assertTrue(repository.deleteById(id));
			verify(dataSource, times(1)).getAuthors();
		}

		@Test
		void delete_shouldDeleteEntity_whenItIsSingleEntityInTheStorage() {
			final long id = 3L;
			final List<AuthorModel> storage = new ArrayList<>();
			storage.add(createTestAuthor(id));
			when(dataSource.getAuthors()).thenReturn(storage);

			assertTrue(repository.deleteById(id));
			verify(dataSource, times(1)).getAuthors();
		}
	}

	@Nested
	class TestExistById {

		@Test
		void existById_shouldReturnFalse_whenStorageIsEmpty() {
			final long id = 99L;
			when(dataSource.getAuthors()).thenReturn(new ArrayList<>());

			assertFalse(repository.existById(id));
		}

		@Test
		void existById_shouldReturnFalse_whenThereIsNoEntityWithGivenId() {
			final long id = 99L;
			final List<AuthorModel> storage = Arrays.asList(
				createTestAuthor(1L),
				createTestAuthor(2L)
			);
			when(dataSource.getAuthors()).thenReturn(storage);

			assertFalse(repository.existById(id));
		}

		@Test
		void existById_shouldReturnTrue_whenEntityWithGivenIdExists() {
			final long id = 2L;
			final List<AuthorModel> storage = Arrays.asList(
				createTestAuthor(1L),
				createTestAuthor(id)
			);
			when(dataSource.getAuthors()).thenReturn(storage);

			assertTrue(repository.existById(id));
		}
	}

	private AuthorModel createTestAuthor(Long authorId) {
		return new AuthorModel(
			authorId,
			"Author Name",
			LocalDateTime.of(2023, 7, 17, 16, 30, 0),
			LocalDateTime.of(2023, 7, 17, 16, 30, 0)
		);
	}
}