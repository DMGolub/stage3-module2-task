package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.utility.DataSource;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class NewsRepositoryTest {

	private final DataSource dataSource = mock(DataSource.class);
	private final BaseRepository<NewsModel, Long> repository = new NewsRepository(dataSource);

	@Nested
	class TestReadAll {

		@Test
		void readAll_shouldReturnEmptyList_whenStorageIsEmpty() {
			BaseRepository<NewsModel, Long> repository = new NewsRepository(dataSource);
			when(dataSource.getNews()).thenReturn(Collections.emptyList());

			assertEquals(Collections.emptyList(), repository.readAll());
			verify(dataSource, times(1)).getNews();
		}

		@Test
		void readAll_shouldReturnTwoEntities_whenThereAreTwoEntitiesInTheStorage() {
			final List<NewsModel> storage = Arrays.asList(
				createTestNews(1L),
				createTestNews(2L)
			);
			when(dataSource.getNews()).thenReturn(storage);

			assertEquals(storage, repository.readAll());
			verify(dataSource, times(1)).getNews();
		}
	}

	@Nested
	class TestReadById {

		@Test
		void readById_shouldReturnEmptyOptional_whenThereIsNoEntityWithGivenId() {
			final List<NewsModel> storage = Arrays.asList(
				createTestNews(1L),
				createTestNews(2L)
			);
			when(dataSource.getNews()).thenReturn(storage);

			assertEquals(Optional.empty(), repository.readById(3L));
			verify(dataSource, times(1)).getNews();
		}

		@Test
		void readById_shouldReturnEntity_whenEntityWithGivenIdExists() {
			final long id = 2L;
			final NewsModel expected = createTestNews(id);
			final List<NewsModel> storage = Arrays.asList(createTestNews(1L), expected);
			when(dataSource.getNews()).thenReturn(storage);

			assertEquals(Optional.of(expected), repository.readById(id));
			verify(dataSource, times(1)).getNews();
		}
	}

	@Nested
	class TestCreate {

		@Test
		void create_shouldSaveEntityAndReturnEntityWithId1_whenStorageIsEmpty() {
			final List<NewsModel> storage = new ArrayList<>();
			when(dataSource.getNews()).thenReturn(storage);

			NewsModel news = createTestNews(null);
			NewsModel expected = createTestNews(1L);

			assertEquals(expected, repository.create(news));
			verify(dataSource, times(1)).getNews();
		}

		@Test
		void create_shouldSaveEntityAndReturnEntityWithId3_whenStorageContainsTwoEntities() {
			final List<NewsModel> storage = new ArrayList<>();
			storage.add(createTestNews(1L));
			storage.add(createTestNews(2L));
			when(dataSource.getNews()).thenReturn(storage);

			NewsModel news = createTestNews(null);
			NewsModel expected = createTestNews(3L);

			assertEquals(expected, repository.create(news));
			verify(dataSource, times(1)).getNews();
		}

		@Test
		void create_shouldSaveEntityAndReturnEntityWithId4_whenStorageContainsTwoEntitiesWithId12And15() {
			final List<NewsModel> storage = new ArrayList<>();
			storage.add(createTestNews(12L));
			storage.add(createTestNews(15L));
			when(dataSource.getNews()).thenReturn(storage);

			NewsModel news = createTestNews(null);
			NewsModel expected = createTestNews(16L);

			assertEquals(expected, repository.create(news));
			verify(dataSource, times(1)).getNews();
		}
	}

	@Nested
	class TestUpdate {

		@Test
		void update_shouldThrowNoSuchElementException_whenThereIsNoEntityWithGivenId() {
			final List<NewsModel> storage = new ArrayList<>();
			storage.add(createTestNews(1L));
			storage.add(createTestNews(2L));
			when(dataSource.getNews()).thenReturn(storage);

			NewsModel updated = createTestNews(99L);

			assertThrows(NoSuchElementException.class, () -> repository.update(updated));
			verify(dataSource, times(1)).getNews();
		}

		@Test
		void update_shouldReturnUpdatedEntity_whenEntityIsValid() {
			final List<NewsModel> storage = new ArrayList<>();
			storage.add(createTestNews(1L));
			storage.add(createTestNews(2L));
			when(dataSource.getNews()).thenReturn(storage);

			final NewsModel updated = createTestNews(2L);
			updated.setTitle("Updated title");
			updated.setContent("Updated content");
			updated.setAuthorId(2L);
			List<NewsModel> expected = Arrays.asList(createTestNews(1L), updated);

			assertEquals(updated, repository.update(updated));
			assertEquals(expected, storage);
			verify(dataSource, times(1)).getNews();
		}
	}

	@Nested
	class TestDeleteById {

		@Test
		void deleteById_shouldReturnFalse_whenStorageIsEmpty() {
			final long id = 99L;
			when(dataSource.getNews()).thenReturn(Collections.emptyList());

			assertFalse(repository.deleteById(id));
			verify(dataSource, times(1)).getNews();
		}

		@Test
		void deleteById_shouldReturnFalse_whenThereIsNoEntityWithGivenId() {
			final long id = 99L;
			final List<NewsModel> storage = Arrays.asList(
				createTestNews(1L),
				createTestNews(2L)
			);
			when(dataSource.getNews()).thenReturn(storage);

			assertFalse(repository.deleteById(id));
			verify(dataSource, times(1)).getNews();
		}

		@Test
		void deleteById_shouldReturnTrue_whenEntityWithGivenIdDeleted() {
			final long id = 3L;
			final List<NewsModel> storage = new ArrayList<>();
			storage.add(createTestNews(1L));
			storage.add(createTestNews(2L));
			storage.add(createTestNews(id));
			storage.add(createTestNews(4L));
			storage.add(createTestNews(5L));
			when(dataSource.getNews()).thenReturn(storage);

			assertTrue(repository.deleteById(id));
			verify(dataSource, times(1)).getNews();
		}

		@Test
		void deleteById_shouldDeleteEntity_whenItIsSingleEntityInTheStorage() {
			final long id = 3L;
			final List<NewsModel> storage = new ArrayList<>();
			storage.add(createTestNews(id));
			when(dataSource.getNews()).thenReturn(storage);

			assertTrue(repository.deleteById(id));
			verify(dataSource, times(1)).getNews();
		}
	}

	@Nested
	class TestExistById {

		@Test
		void existById_shouldReturnFalse_whenStorageIsEmpty() {
			final long id = 99L;
			when(dataSource.getNews()).thenReturn(Collections.emptyList());

			assertFalse(repository.existById(id));
			verify(dataSource, times(1)).getNews();
		}

		@Test
		void existById_shouldReturnFalse_whenThereIsNoEntityWithGivenId() {
			final long id = 99L;
			final List<NewsModel> storage = Arrays.asList(
				createTestNews(1L),
				createTestNews(2L)
			);
			when(dataSource.getNews()).thenReturn(storage);

			assertFalse(repository.existById(id));
			verify(dataSource, times(1)).getNews();
		}

		@Test
		void existById_shouldReturnTrue_whenEntityWithGivenIdExists() {
			final long id = 2L;
			final List<NewsModel> storage = Arrays.asList(
				createTestNews(1L),
				createTestNews(id)
			);
			when(dataSource.getNews()).thenReturn(storage);

			assertTrue(repository.existById(id));
			verify(dataSource, times(1)).getNews();
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
}