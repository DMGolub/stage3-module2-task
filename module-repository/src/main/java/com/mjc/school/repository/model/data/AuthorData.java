package com.mjc.school.repository.model.data;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.utility.Utilities;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorData {

	private static final String AUTHOR_FILE_NAME = "authors";
	private List<AuthorModel> authors;

	public AuthorData() {
		// Empty
	}

	@PostConstruct
	private void init() {
		List<String> names = Utilities.readLinesFromFile(AUTHOR_FILE_NAME);
		authors = new ArrayList<>();
		for (int i = 1; i < names.size(); i++) {
			LocalDateTime date = Utilities.getRandomDate();
			authors.add(new AuthorModel(
				(long) i,
				names.get(i),
				date,
				date
			));
		}
	}

	public List<AuthorModel> getAuthors() {
		return authors;
	}
}