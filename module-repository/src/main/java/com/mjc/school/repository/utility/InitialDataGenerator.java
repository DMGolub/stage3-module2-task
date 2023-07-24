package com.mjc.school.repository.utility;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.data.AuthorData;
import com.mjc.school.repository.model.data.NewsData;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class InitialDataGenerator {

	private final AuthorData authorData;
	private final NewsData newsData;
	private List<AuthorModel> authors;
	private List<NewsModel> news;

	public InitialDataGenerator(final AuthorData authorData, final NewsData newsData) {
		this.authorData = authorData;
		this.newsData = newsData;
	}

	@PostConstruct
	private void init() {
		authors = authorData.getAuthors();
		news = newsData.getNews();
	}

	public List<AuthorModel> getAuthors() {
		return authors;
	}

	public List<NewsModel> getNews() {
		return news;
	}
}