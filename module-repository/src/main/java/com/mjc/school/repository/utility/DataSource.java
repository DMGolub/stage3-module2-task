package com.mjc.school.repository.utility;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSource {

	private final List<AuthorModel> authors;
	private final List<NewsModel> news;


	public DataSource() {
		authors = InitialDataGenerator.getInstance().getAuthors();
		news = InitialDataGenerator.getInstance().getNews();
	}

	public List<AuthorModel> getAuthors() {
		return authors;
	}
	public List<NewsModel> getNews() {
		return news;
	}
}