package com.mjc.school.repository.utility;

import com.mjc.school.repository.model.BaseEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Utilities {

	private Utilities() {
		// Empty. Hiding default public constructor
	}

	/**
	 * Finds maximum entity id value in the given storage.
	 *
	 * @param storage List of entities.
	 * @return long maximum id if found and 0 otherwise.
	 * @param <T> entity class extending BaseEntity.
	 */
	public static <T extends BaseEntity<Long>> long getMaxId(final List<T> storage) {
		return storage.stream()
			.map(BaseEntity::getId)
			.max(Comparator.naturalOrder())
			.orElse(0L);
	}

	public static List<String> readLinesFromFile(final String fileName) {
		List<String> lines = null;
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try (BufferedReader reader = new BufferedReader(
			new InputStreamReader(Objects.requireNonNull(classLoader.getResourceAsStream(fileName))))
		) {
			lines = reader.lines().toList();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return lines;
	}

	public static LocalDateTime getRandomDate() {
		final int minus_days_max = 30;
		Random random = new Random();
		LocalDate date = LocalDate.now().minusDays(random.nextInt(minus_days_max));
		LocalTime time = LocalTime.of(
			random.nextInt(0, 24),			// hours
			random.nextInt(0, 60), 		// minutes
			random.nextInt(0, 60)			// seconds
		);
		return LocalDateTime.of(date, time);
	}

	public static <E> E getRandomElement(final List<E> list) {
		Random random = new Random();
		return list.get(random.nextInt(0, list.size()));
	}
}