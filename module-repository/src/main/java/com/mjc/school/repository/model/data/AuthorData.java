package com.mjc.school.repository.model.data;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.utility.Utilities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuthorData {

	private static final String AUTHOR_FILE_NAME = "authors";
	private static AuthorData instance;
	private List<AuthorModel> authors;

	private AuthorData() {
		init();
	}

	public static AuthorData getAuthorData() {
		if (instance == null) {
			instance = new AuthorData();
		}
		return instance;
	}

	public List<AuthorModel> getAuthors() {
		return authors;
	}

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
}