package com.mjc.school.repository.utility;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;

import java.util.List;

public class DataSource {

	private static DataSource instance;
	private final List<AuthorModel> authors;
	private final List<NewsModel> news;


	private DataSource() {
		authors = InitialDataGenerator.getInstance().getAuthors();
		news = InitialDataGenerator.getInstance().getNews();
	}

	public static DataSource getInstance() {
		if (instance == null) {
			instance = new DataSource();
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