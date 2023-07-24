package com.mjc.school.repository.utility;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class DataSource {

	private final InitialDataGenerator dataGenerator;
	private List<AuthorModel> authors;
	private List<NewsModel> news;

	public DataSource(final InitialDataGenerator dataGenerator) {
		this.dataGenerator = dataGenerator;
	}

	@PostConstruct
	public void init() {
		authors = dataGenerator.getAuthors();
		news = dataGenerator.getNews();
	}

	public List<AuthorModel> getAuthors() {
		return authors;
	}
	public List<NewsModel> getNews() {
		return news;
	}
}