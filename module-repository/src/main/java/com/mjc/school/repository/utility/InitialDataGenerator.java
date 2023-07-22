package com.mjc.school.repository.utility;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;

import java.util.List;

import static com.mjc.school.repository.model.data.AuthorData.getAuthorData;
import static com.mjc.school.repository.model.data.NewsData.getNewsData;

public class InitialDataGenerator {


	private static InitialDataGenerator instance;
	private final List<AuthorModel> authors;
	private final List<NewsModel> news;

	private InitialDataGenerator() {
		authors = getAuthorData().getAuthors();
		news = getNewsData(authors).getNews();
	}

	public static InitialDataGenerator getInstance() {
		if (instance == null) {
			instance = new InitialDataGenerator();
		}
		return instance;
	}

	public List<AuthorModel> getAuthors() {
		return authors;
	}

	public List<NewsModel> getNews() {
		return news;
	}
}