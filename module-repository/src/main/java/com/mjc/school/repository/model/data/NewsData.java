package com.mjc.school.repository.model.data;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.utility.Utilities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewsData {

	private static final String NEWS_FILE_NAME = "news";
	private static final String CONTENT_FILE_NAME = "content";
	private static final long NEWS_AMOUNT = 20;
	private static NewsData instance;
	private List<NewsModel> news;

	private NewsData(List<AuthorModel> authors) {
		init(authors);
	}

	public static NewsData getNewsData(List<AuthorModel> authors) {
		if (instance == null) {
			instance = new NewsData(authors);
		}
		return instance;
	}

	public List<NewsModel> getNews() {
		return news;
	}

	private void init(List<AuthorModel> authors) {
		List<String> titles = Utilities.readLinesFromFile(NEWS_FILE_NAME);
		List<String> content = Utilities.readLinesFromFile(CONTENT_FILE_NAME);
		news = new ArrayList<>();
		Random random = new Random();
		for (long i = 1; i <= NEWS_AMOUNT; i++) {
			LocalDateTime date = Utilities.getRandomDate();
			news.add(new NewsModel(
				i,													// id
				Utilities.getRandomElement(titles), 				// title
				Utilities.getRandomElement(content), 				// content
				date,												// createDate
				date,												// lastUpdateDate
				authors.get(random.nextInt(authors.size())).getId()	// authorId
			));
		}
	}
}