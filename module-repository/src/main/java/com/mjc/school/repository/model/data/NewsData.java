package com.mjc.school.repository.model.data;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.utility.Utilities;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class NewsData {

	private static final String NEWS_FILE_NAME = "news";
	private static final String CONTENT_FILE_NAME = "content";
	private static final long NEWS_AMOUNT = 20;
	private final AuthorData authorData;
	private List<NewsModel> news;

	public NewsData(final AuthorData authorData) {
		this.authorData = authorData;
	}

	@PostConstruct
	private void init() {
		List<AuthorModel> authors = authorData.getAuthors();
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

	public List<NewsModel> getNews() {
		return news;
	}
}