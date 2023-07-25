package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.annotation.OnDelete;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.utility.DataSource;
import com.mjc.school.repository.utility.Utilities;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class AuthorRepository implements BaseRepository<AuthorModel, Long> {

	private final DataSource dataSource;

	public AuthorRepository(final DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<AuthorModel> readAll() {
		return dataSource.getAuthors();
	}

	@Override
	public Optional<AuthorModel> readById(final Long authorId) {
		return dataSource.getAuthors().stream()
			.filter(author -> authorId.equals(author.getId()))
			.findFirst();
	}

	@Override
	public AuthorModel create(final AuthorModel author) {
		final List<AuthorModel> allAuthors = dataSource.getAuthors();
		author.setId(Utilities.getMaxId(allAuthors) + 1);
		allAuthors.add(author);
		return author;
	}

	@Override
	public AuthorModel update(final AuthorModel updatedAuthor) throws NoSuchElementException {
		final AuthorModel author = readById(updatedAuthor.getId()).get();
		author.setName(updatedAuthor.getName());
		author.setLastUpdateDate(updatedAuthor.getLastUpdateDate());
		return author;
	}

	@Override
	@OnDelete("set null")
	public boolean deleteById(final Long id) {
		return dataSource.getAuthors().removeIf(a -> a.getId().equals(id));
	}

	@Override
	public boolean existById(final Long authorId) {
		return dataSource.getAuthors().stream()
			.anyMatch(author -> authorId.equals(author.getId()));
	}
}